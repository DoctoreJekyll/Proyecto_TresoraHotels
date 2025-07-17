package com.atm.buenas_practicas_java.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String mostrarPanel(Model model, Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String titulo;

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            titulo = "Bienvenido al Panel de Administradores";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLEADO"))) {
            titulo = "Bienvenido al Panel de Empleados";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_LIMPIEZA"))) {
            titulo = "Bienvenido al Panel de Personal de Limpieza";
        } else {
            titulo = "Bienvenido al Panel de Administración";
        }

        model.addAttribute("tituloPanel", titulo);
        return "panel";
    }
}