package com.atm.buenas_practicas_java.controllers;


import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final UsuarioService usuarioService;
    private final HotelService hotelService;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String email = auth.getName();
            usuarioService.findByEmail(email).ifPresent(usuario -> model.addAttribute("usuario", usuario));
        }

        model.addAttribute("hotels", hotelService.findAll());
    }
}
