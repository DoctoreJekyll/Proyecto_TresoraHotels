package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.services.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservas")
public class ReservasController {

    private final ReservaService reservaService;

    public ReservasController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioReservas(Model model) {
        return null;
    }

    //Formulario de reserva rapida, usando entidades o dtos + los servicios necesarios
    //Formulario general que acceda a hotelService, reserva, habitaciones y usuarios
    //Para ambos casos tocaria hacer un dto por cada "controlador" o endpoint
    //Para esto podemos hacer una fachada que consuma esto
    //De aqui un controlador que consuma la fachada y llame a ambos formularios
    //habra que hacer mappers tambien usando el abstract
    //pòsiblemente toque hacer converters


}
