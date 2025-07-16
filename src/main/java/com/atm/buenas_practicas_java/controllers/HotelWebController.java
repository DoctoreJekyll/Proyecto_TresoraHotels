package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.HabitacionesDisponiblesDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.mappers.HabitacionMapper;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpSession;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.LocalDate;
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
        Hotel hotel = hotelService.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel no encontrado"));

        Pageable paginacion = PageRequest.of(pageable.getPageNumber(), 3, Sort.by("id"));
        Page<Habitacion> habitaciones;

        // Si se han proporcionado fechas, filtrar y paginar por disponibilidad
        if (fechaEntrada != null && !fechaEntrada.isEmpty() && fechaSalida != null && !fechaSalida.isEmpty()) {
            try {
                // Obtener solo los IDs de las habitaciones disponibles para esas fechas
                List<Habitacion> habitacionesDisponiblesList = habitacionService.obtenerDisponiblesPorHotelYFechas(hotel.getId(), fechaEntrada, fechaSalida);
                List<Integer> idsHabitacionesDisponibles = habitacionesDisponiblesList.stream()
                        .map(Habitacion::getId)
                        .toList();

                // Si no hay habitaciones disponibles, o si la lista de IDs está vacía, devolver una página vacía
                if (idsHabitacionesDisponibles.isEmpty()) {
                    habitaciones = Page.empty(paginacion);
                } else {
                    // Ahora, paginar solo las habitaciones que están disponibles
                    habitaciones = habitacionService.findByHotelIdAndIdsIn(hotel.getId(), idsHabitacionesDisponibles, paginacion);
                }

            } catch (DateTimeParseException e) {
                model.addAttribute("errorFecha", "Formato de fecha inválido. Utiliza YYYY-MM-DD.");
                habitaciones = Page.empty(paginacion); // En caso de error de fecha, no mostrar habitaciones
            }
        } else {
            // Si NO hay fechas, devolver una página vacía para que no se muestre nada por defecto
            habitaciones = Page.empty(paginacion);
        }

        for (Habitacion h : habitaciones) {
            if (h.getProducto() != null) {
                h.getProducto().getPrecioBase(); // fuerza inicialización
            }
        }

        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitaciones", habitaciones); // Esto contendrá la página vacía si no hay fechas

        model.addAttribute("fechaEntrada", fechaEntrada);
        model.addAttribute("fechaSalida", fechaSalida);

        return "hotelesReservas";
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