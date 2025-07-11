package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // 1️⃣ Mostrar el formulario de reserva rápida
    @GetMapping("/rapida")
    public String mostrarFormularioReservaRapida(Model model) {
        List<Habitacion> habitaciones = habitacionService.findAllConHotel();

        // Agrupar habitaciones por hotel
        Map<Hotel, List<Habitacion>> habitacionesPorHotel = habitaciones.stream()
                .collect(Collectors.groupingBy(Habitacion::getHotel, LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("reservaDTO", new ReservaRapidaDTO());
        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("habitacionesPorHotel", habitacionesPorHotel);
        return "reservaRapida";
    }

    // 2️⃣ Procesar el envío del formulario
    @PostMapping("/rapida")
    public String procesarReservaRapida(
            @ModelAttribute("reservaDTO") ReservaRapidaDTO dto,
            Model model
    ) {
        Reserva reservaGuardada = reservaService.crearReservaConProductos(dto);
        ConfirmacionReservaDTO confirmacion = confirmacionReservaDTO.toDto(reservaGuardada, dto);
        model.addAttribute("reservaConfirmada", confirmacion);
        return "confirmacionReservaRapida";
    }

}
