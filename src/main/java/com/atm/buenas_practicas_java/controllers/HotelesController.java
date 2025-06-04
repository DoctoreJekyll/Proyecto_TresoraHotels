package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.dtos.*;
import com.atm.buenas_practicas_java.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HotelesController {
    @GetMapping("/")
    public String vistaHome( ModelMap interfazConPantalla){
        return "home";
    }

    @GetMapping("/servicios")
    public String vistaservicios( ){
        return "services";
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model){
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@ejemplo.com");
        model.addAttribute("datoslogin", loginDto);
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute(name="datoslogin") LoginDto login){
        System.out.println(login.getEmail());
        return "user_home_page";
    }


    @GetMapping("/crearCuenta")
    public String mostrarCrearCuenta(Model model ){
        UserDto userDto = new UserDto();
        model.addAttribute("userData", userDto);
        return "crearCuenta";
    }

    private final EmailService emailService;

    @Autowired
    public HotelesController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/crearCuenta")
    public String postCrearCuenta(@ModelAttribute(name = "userData") UserDto user){
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        emailService.sendEmail(
                "jrmar0805@gmail.com",
                user.getEmail(),
                "Creacion de cuenta en Tresora",
                "Has creado tu cuenta con exito, estas son tus credenciales de usuario" + user.getEmail() + user.getPassword()
        );

        System.out.println("email sent");

        return "home";
    }

    @GetMapping("/userhome")
    public String mostrarUserHome( ) {
        return "user_home_page";
    }

    @GetMapping("/hoteles")
    public String mostrarHoteles(Model model ){
        List<HotelDto> hotels = HotelsData.hotelsDataList();
        model.addAttribute("hotels", hotels);

        return "hoteles_web";
    }

    @GetMapping("/contact")
    public String mostrarPaginaContact(ModelMap intefrazConPantalla) {
        ContactDto contacto = new ContactDto();
        contacto.setNombre("El lobo feroz");
        intefrazConPantalla.addAttribute("datoscontacto", contacto);
        return "contact";
    }

    @PostMapping("/contact")
    public String postMostrarPaginaContact(@ModelAttribute(name="datoscontacto") ContactDto infoContacto) {
        System.out.println(infoContacto.getNombre());
        System.out.println(infoContacto.getMensaje());
        return "home";
    }

    @GetMapping("/reserva")
    public String reserva( ModelMap ModelReserva) {
        ReservaDto reserva= new ReservaDto();
        reserva.setAdultos(4);
        ModelReserva.addAttribute("datosreserva", reserva);
        return "reservaCompleta";
    }

    @PostMapping("/reserva")
    public String postReserva(@ModelAttribute(name="datosreserva") ReservaDto reserva) {
        System.out.println(reserva.getAdultos());
        System.out.println(reserva.getFechaEntrada());
        System.out.println(reserva.getFechaSalida());
        return "home";
    }

    @GetMapping("/reservarapida")
    public String reservarapida( ) {
        return "reservaRapida";
    }

    @GetMapping("/confirmarReserva")
    public String confirmarreserva( ) {
        return "confirmarReserva";
    }




}
