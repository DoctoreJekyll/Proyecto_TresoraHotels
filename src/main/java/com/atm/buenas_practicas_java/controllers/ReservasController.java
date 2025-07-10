package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservas")
public class ReservasController {

    private final ReservaService reservaService;
    private final ProductoService productoService; // Para mostrar productos en el formulario
    private final HabitacionService habitacionService;

    public ReservasController(ReservaService reservaService, ProductoService productoService, HabitacionService habitacionService) {
        this.reservaService = reservaService;
        this.productoService = productoService;
        this.habitacionService = habitacionService;
    }

    // 1️⃣ Mostrar el formulario de reserva rápida
    @GetMapping("/rapida")
    public String mostrarFormularioReservaRapida(Model model) {
        model.addAttribute("reservaDTO", new ReservaRapidaDTO());
        model.addAttribute("productos", productoService.obtenerProductosActivos());
        model.addAttribute("habitaciones", habitacionService.findAll());
        return "reservaRapida";
    }

    // 2️⃣ Procesar el envío del formulario
    @PostMapping("/rapida")
    public String procesarReservaRapida(
            @ModelAttribute("reservaDTO") ReservaRapidaDTO dto,
            Model model
    ) {
        Reserva reservaGuardada = reservaService.crearReservaConProductos(dto);
        model.addAttribute("reserva", reservaGuardada);
        return "confirmacionReservaRapida";
    }

}
