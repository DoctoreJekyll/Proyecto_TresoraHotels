package com.atm.buenas_practicas_java.controllers;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.LimpiezaHabitacion;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.InformesService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/informes")
public class InformesController {

    private final InformesService informesService;
    private final UsuarioService usuarioService;
    private final HabitacionService habitacionService;

    public InformesController(InformesService informesService, UsuarioService usuarioService, HabitacionService habitacionService) {
        this.informesService = informesService;
        this.usuarioService = usuarioService;
        this.habitacionService = habitacionService;
    }

    // Mostrar formulario para nuevo informe
    @GetMapping("/nuevo")
    public String mostrarFormularioInforme(Model model) {
        LimpiezaHabitacion  limpiezaHabitacion = new LimpiezaHabitacion();
        model.addAttribute("limpiezaHabitacion", limpiezaHabitacion);

        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);

        List<Habitacion> habitaciones = new ArrayList<>();
        habitaciones = habitacionService.findAll();
        model.addAttribute("habitaciones", habitaciones);

        return "informeForm";  // El nombre de tu vista Thymeleaf para el formulario
    }

    // Procesar envío del formulario
    @PostMapping("/guardar")
    public String procesarFormularioInforme(
            @ModelAttribute("limpiezaHabitacion") LimpiezaHabitacion limpiezaHabitacion) {

        informesService.save(limpiezaHabitacion);

        return "redirect:/informes/nuevo";  // o a donde quieras redirigir después
    }
}
