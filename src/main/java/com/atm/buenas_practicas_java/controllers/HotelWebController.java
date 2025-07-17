package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.HabitacionesDisponiblesDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.mappers.HabitacionMapper;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpSession;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelWebController {

    private final HotelService hotelService;
    private final HabitacionService habitacionService;
    private final ProductoService productoService;
    private final ReservaService reservaService;
    private final ReservaConfirmacionMapper confirmacionReservaDTO;
    private final HabitacionMapper habitacionMapper;
    private final UsuarioService usuarioService;

    @GetMapping("/api/habitacionesDisponibles")
    @ResponseBody
    public List<HabitacionesDisponiblesDTO> obtenerHabitacionesDisponibles(
            @RequestParam Integer hotelId,
            @RequestParam String fechaEntrada,
            @RequestParam String fechaSalida
    ) {
        List<Habitacion> disponibles = habitacionService.obtenerDisponiblesPorHotelYFechas(hotelId, fechaEntrada, fechaSalida);
        return habitacionMapper.toDtoList(disponibles);
    }

    @GetMapping("/{nombre}")
    @Transactional(readOnly = true)
    public String mostrarHotelPorNombre(
            @PathVariable String nombre,
            @RequestParam(required = false) String fechaEntrada,
            @RequestParam(required = false) String fechaSalida,
            Model model,
            Pageable pageable,
            HttpSession session) {

        session.getAttribute("dummy"); // Forzar creación de sesión

        // 1. Obtener el hotel
        Hotel hotel = hotelService.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel no encontrado"));

        // 2. Configurar la paginación base
        Pageable paginacion = PageRequest.of(pageable.getPageNumber(), 3, Sort.by("id"));

        // 3. Obtener habitaciones paginadas y filtradas por disponibilidad
        Page<Habitacion> habitaciones = getFilteredAndPaginatedRooms(hotel.getId(), fechaEntrada, fechaSalida, paginacion, model);

        // 4. Forzar inicialización de Producto (si es necesario por Lazy Loading)
        habitaciones.forEach(h -> {
            if (h.getProducto() != null) {
                h.getProducto().getPrecioBase();
            }
        });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            String userName = usuario.getNombre();

            model.addAttribute("usuarioLogeadoEmail", userEmail);
            model.addAttribute("usuarioLogeadoName", userName);
        }

        // 5. Añadir atributos al modelo
        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitaciones", habitaciones);
        model.addAttribute("fechaEntrada", fechaEntrada);
        model.addAttribute("fechaSalida", fechaSalida);

        return "hotelesReservas";
    }

    /**
     * Helper method to get filtered and paginated rooms based on dates.
     * @param hotelId The ID of the hotel.
     * @param fechaEntrada The check-in date string.
     * @param fechaSalida The check-out date string.
     * @param pageable The pagination information.
     * @param model The Model to add error attributes.
     * @return A Page of Habitacion entities.
     */
    private Page<Habitacion> getFilteredAndPaginatedRooms(Integer hotelId, String fechaEntrada, String fechaSalida, Pageable pageable, Model model) {
        if (fechaEntrada != null && !fechaEntrada.isEmpty() && fechaSalida != null && !fechaSalida.isEmpty()) {
            try {
                List<Habitacion> habitacionesDisponiblesList = habitacionService.obtenerDisponiblesPorHotelYFechas(hotelId, fechaEntrada, fechaSalida);
                List<Integer> idsHabitacionesDisponibles = habitacionesDisponiblesList.stream()
                        .map(Habitacion::getId)
                        .toList();

                if (idsHabitacionesDisponibles.isEmpty()) {
                    return Page.empty(pageable);
                } else {
                    return habitacionService.findByHotelIdAndIdsIn(hotelId, idsHabitacionesDisponibles, pageable);
                }
            } catch (DateTimeParseException e) {
                model.addAttribute("errorFecha", "Formato de fecha inválido. Utiliza YYYY-MM-DD.");
                return Page.empty(pageable); // Return empty page on date parsing error
            }
        } else {
            return Page.empty(pageable); // Return empty page if no dates are provided
        }
    }

    @PostMapping("/reservaCompleta")
    @Transactional
    public String procesarReservaRapida(
            @ModelAttribute("reservaDTO") ReservaRapidaDTO dto,
            Model model
    ) {
        Reserva reservaGuardada = reservaService.crearReservaConProductos(dto);

        ConfirmacionReservaDTO confirmacion = confirmacionReservaDTO.toDto(reservaGuardada, dto);

        long diasEstancia = ChronoUnit.DAYS.between(confirmacion.getFechaEntrada(), confirmacion.getFechaSalida());
        model.addAttribute("diasEstancia", diasEstancia);
        model.addAttribute("reservaConfirmada", confirmacion);
        return "confirmacionReservaRapida";
    }
}