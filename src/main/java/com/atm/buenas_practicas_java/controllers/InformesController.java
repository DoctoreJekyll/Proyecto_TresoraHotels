package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.InformeLimpieza;
import com.atm.buenas_practicas_java.entities.Usuario;
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

    // Mostrar lista de informes dummy en /informes o /informes/lista
    @GetMapping({"", "/lista"})
    public String mostrarInformes(Model model) {
        List<InformeLimpieza> informes = informesDummy();
        model.addAttribute("informes", informes);
        return "informeVista";  // El nombre de tu vista Thymeleaf para mostrar informes
    }

    // Mostrar formulario para nuevo informe
    @GetMapping("/nuevo")
    public String mostrarFormularioInforme(Model model) {
        model.addAttribute("informe", new InformeLimpieza());
        model.addAttribute("usuarios", datosUsuariosDummy());
        model.addAttribute("habitaciones", datosHabitacionesDummy());
        return "informeForm";  // El nombre de tu vista Thymeleaf para el formulario
    }

    // Procesar envío del formulario (simulado)
    @PostMapping("/nuevo")
    public String procesarFormularioInforme(@ModelAttribute InformeLimpieza informe, Model model) {
        System.out.println("Informe recibido: " + informe);

        // Simulamos guardado y recargamos formulario vacío con mensaje y datos dummy
        model.addAttribute("mensaje", "Informe enviado correctamente (simulado).");
        model.addAttribute("informe", new InformeLimpieza());
        model.addAttribute("usuarios", datosUsuariosDummy());
        model.addAttribute("habitaciones", datosHabitacionesDummy());
        return "informeForm";
    }

    // Datos dummy para usuarios
    private List<Usuario> datosUsuariosDummy() {
        List<Usuario> usuarios = new ArrayList<>();

        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setNombre("Juan");
        u1.setApellidos("Pérez");

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setNombre("María");
        u2.setApellidos("Gómez");

        usuarios.add(u1);
        usuarios.add(u2);

        return usuarios;
    }

    // Datos dummy para habitaciones
    private List<Habitacion> datosHabitacionesDummy() {
        List<Habitacion> habitaciones = new ArrayList<>();

        Habitacion h1 = new Habitacion();
        h1.setId(101L);
        h1.setNumeroHabitacion(101);
        h1.setPiso(1);

        Habitacion h2 = new Habitacion();
        h2.setId(102L);
        h2.setNumeroHabitacion(102);
        h2.setPiso(1);

        habitaciones.add(h1);
        habitaciones.add(h2);

        return habitaciones;
    }

    // Datos dummy para informes
    private List<InformeLimpieza> informesDummy() {
        List<InformeLimpieza> informes = new ArrayList<>();
        List<Usuario> usuarios = datosUsuariosDummy();
        List<Habitacion> habitaciones = datosHabitacionesDummy();

        InformeLimpieza i1 = new InformeLimpieza();
        i1.setId(1L);
        i1.setUsuario(usuarios.get(0));
        i1.setHabitacion(habitaciones.get(0));
        i1.setFechaLimpieza(LocalDate.of(2025, 6, 15));
        i1.setHoraLimpieza(LocalTime.of(10, 30));
        i1.setFoto1("foto1_1.jpg");
        i1.setFoto2("foto1_2.jpg");

        InformeLimpieza i2 = new InformeLimpieza();
        i2.setId(2L);
        i2.setUsuario(usuarios.get(1));
        i2.setHabitacion(habitaciones.get(1));
        i2.setFechaLimpieza(LocalDate.of(2025, 6, 16));
        i2.setHoraLimpieza(LocalTime.of(9, 0));
        i2.setFoto1(null);
        i2.setFoto2("foto2_2.jpg");

        informes.add(i1);
        informes.add(i2);

        return informes;
    }
}
