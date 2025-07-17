package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.repositories.HotelesRepo;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.ReservaService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservasController {

    private final ReservaService reservaService;
    private final ProductoService productoService; // Para mostrar productos en el formulario
    private final HabitacionService habitacionService;
    private final ReservaConfirmacionMapper confirmacionReservaDTO;
    private final HotelesRepo hotelesRepo;
    private final UsuarioService usuarioService;

    @GetMapping("/api/habitaciones")
    @ResponseBody
    public List<Map<String, Object>> getHabitacionesDisponibles(
            @RequestParam("hotelId") Integer hotelId,
            @RequestParam("fechaEntrada") String fechaEntradaStr,
            @RequestParam("fechaSalida") String fechaSalidaStr,
            HttpSession session
    ) {

        session.getAttribute("dummy");

        LocalDate fechaEntrada = LocalDate.parse(fechaEntradaStr);
        LocalDate fechaSalida = LocalDate.parse(fechaSalidaStr);

        return habitacionService.findDisponiblesPorHotelYFechas(hotelId, fechaEntrada, fechaSalida)
                .stream()
                .map(h -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", h.getId());
                    map.put("tipo", h.getTipo());
                    map.put("precioBase", h.getProducto().getPrecioBase());
                    map.put("numeroHabitacion", h.getNumeroHabitacion());
                    return map;
                })
                .toList();
    }


    // 1️⃣ Mostrar el formulario de reserva rápida
    @GetMapping("/rapida")
    public String mostrarFormularioReservaRapida(Model model) {
        List<Habitacion> habitaciones = habitacionService.findAllConHotel();

        // Agrupar habitaciones por hotel
        Map<Hotel, List<Habitacion>> habitacionesPorHotel = habitaciones.stream()
                .collect(Collectors.groupingBy(Habitacion::getHotel, LinkedHashMap::new, Collectors.toList()));

        ReservaRapidaDTO dto = new ReservaRapidaDTO(); // Siempre creamos el DTO

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            dto.setNombre(usuario.getNombre());
            dto.setEmail(authentication.getName());
            dto.setIdUsuario(usuario.getId());
        }

        // El resto de campos quedan en null (vacíos en el formulario)
        model.addAttribute("reservaDTO", dto);
        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("habitacionesPorHotel", habitacionesPorHotel);
        model.addAttribute("hoteles", hotelesRepo.findAll());

        return "reservaRapida";
    }

    // 2️⃣ Procesar el envío del formulario
    @PostMapping("/rapida")
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
