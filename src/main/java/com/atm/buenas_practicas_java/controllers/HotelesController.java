package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.dtos.ContactDto;
import com.atm.buenas_practicas_java.dtos.LoginDto;
import com.atm.buenas_practicas_java.dtos.ReservaDto;
import com.atm.buenas_practicas_java.dtos.UserDto;
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

    @GetMapping("/contact")
    public String mostrarPaginaContact(ModelMap intefrazConPantalla) {
        ContactDto contacto = new ContactDto();
        contacto.setName("El lobo feroz");
        intefrazConPantalla.addAttribute("contactInfo", contacto);
        return "contact";
    }

    @PostMapping("/contact")
    public String postMostrarPaginaContact(@ModelAttribute(name="contactInfo") ContactDto contactInfo) {
        System.out.println(contactInfo.getName());
        System.out.println(contactInfo.getMessage());

        emailService.sendEmail(

                "alba2gr@gmail.com",
                contactInfo.getEmail(),
                "Gracias por contactar con Tresora",
                "Hola " + contactInfo.getName() + ",\n\n" +
                        "Hemos recibido tu mensaje:\n" +
                        contactInfo.getMessage() + "\n\n" +
                        "Te contactaremos pronto.\n\n" +
                        "Atentamente,\nTresora Hotels"
        );
        return "home";
    }

    @GetMapping("/reservaPlaya")
    public String reservaPlaya( ModelMap ModelReserva) {
        ReservaDto reserva= new ReservaDto();
        reserva.setAdultos(4);
        ModelReserva.addAttribute("datosreserva", reserva);
        return "reservaHotelPlaya";
    }

    @PostMapping("/reservaPlaya")
    public String postReservaPlaya(@ModelAttribute(name="datosreserva") ReservaDto reserva) {
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


    @GetMapping("/reservaMontaña")
    public String reservaMontaña( ModelMap ModelReserva) {
        ReservaDto reserva= new ReservaDto();
        reserva.setAdultos(4);
        ModelReserva.addAttribute("datosreserva", reserva);
        return "reservaHotelMontaña";
    }

    @PostMapping("/reservaMontaña")
    public String postReservaMontaña(@ModelAttribute(name="datosreserva") ReservaDto reserva) {
        System.out.println(reserva.getAdultos());
        System.out.println(reserva.getFechaEntrada());
        System.out.println(reserva.getFechaSalida());
        return "home";
    }

}
