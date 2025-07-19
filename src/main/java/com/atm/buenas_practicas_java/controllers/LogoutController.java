package com.atm.buenas_practicas_java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout-exito")
    public String logoutExito() {
        return "logout-exito";
    }
}
