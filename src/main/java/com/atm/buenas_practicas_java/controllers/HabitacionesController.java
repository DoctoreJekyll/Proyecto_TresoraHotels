package com.atm.buenas_practicas_java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionesController {

    // Mostrar lista de informes dummy en /informes o /informes/lista
    @GetMapping({"", "/lista"})
    public String mostrarInformes(Model model) {
        return "informeVista";  // El nombre de tu vista Thymeleaf para mostrar informes
    }

    // Mostrar formulario para nuevo informe
    @GetMapping("/nuevo")
    public String mostrarFormularioInforme(Model model) {
        return "habitacionForm";  // El nombre de tu vista Thymeleaf para el formulario
    }

    // Procesar envío del formulario (simulado)
    @PostMapping("/nuevo")
    public String procesarFormularioInforme(@ModelAttribute Model model) {
        return "habitacionForm";
    }



}
