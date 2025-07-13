package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.dtosOld.*;
import com.atm.buenas_practicas_java.entities.DatosReserva;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.services.EmailService;
import com.atm.buenas_practicas_java.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HotelesController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hoteles")
    public String mostrarHoteles(Model model) {
        List<Hotel> hotels = hotelService.findAll();
        System.out.println("Hoteles obtenidos: " + hotels); // Para depuración
        for (Hotel hotel : hotels) {
            System.out.println("ID: " + hotel.getId() +
                    ", Nombre: " + hotel.getNombre() +
                    ", Descripción: " + hotel.getDescripcion() +
                    ", Imagen: " + hotel.getImageURL() +
                    ", DireccionURL: " + hotel.getDireccionURL());
        }
        model.addAttribute("hotels", hotels);
        return "home";
    }
    @GetMapping("/reservaFlotante")
    public String mostrarFormularioReserva(
            @RequestParam(value = "hotelId", required = false) Integer hotelId,
            @RequestParam(value = "fechaEntrada", required = false) String fechaEntrada,
            @RequestParam(value = "fechaSalida", required = false) String fechaSalida,
            @RequestParam(value = "habitacionesAdultos", required = false) String habitacionesAdultos,
            Model model) {
        // Obtener todos los hoteles para el formulario
        List<Hotel> hotels = hotelService.findAll();
        model.addAttribute("hotels", hotels);

        // Crear un objeto para los datos de la reserva
        DatosReserva datosReserva = new DatosReserva();
        datosReserva.setFechaEntrada(fechaEntrada);
        datosReserva.setFechaSalida(fechaSalida);
        datosReserva.setHabitacionesAdultos(habitacionesAdultos);

        // Si se proporcionó un hotelId, obtener el hotel seleccionado
        String templateName = "reservaHotelDefault"; // Plantilla por defecto si no se selecciona hotel
        if (hotelId != null) {
            Optional<Hotel> hotelOptional = hotelService.findById(hotelId);
            if (hotelOptional.isPresent()) {
                Hotel hotel = hotelOptional.get();
                model.addAttribute("hotelSeleccionado", hotel);
                model.addAttribute("hotelNombre", hotel.getNombre());
                datosReserva.setHotelId(hotelId);

                // Determinar la plantilla según el hotel seleccionado
                switch (hotelId) {
                    case 1:
                        templateName = "reservaHotelPlaya";
                        break;
                    case 2:
                        templateName = "reservaHotelMontaña";
                        break;
                    case 3:
                        templateName = "reservaHotelCiudad";
                        break;
                    default:
                        templateName = "reservaHotelDefault";
                        break;
                }
            } else {
                model.addAttribute("hotelNombre", "No se seleccionó un hotel válido");
            }
        } else {
            model.addAttribute("hotelNombre", "No se seleccionó un hotel");
        }

        // Agregar datos al modelo
        model.addAttribute("datosReserva", datosReserva);
        model.addAttribute("fechaEntrada", fechaEntrada != null ? fechaEntrada : "No especificada");
        model.addAttribute("fechaSalida", fechaSalida != null ? fechaSalida : "No especificada");
        model.addAttribute("habitacionesAdultos", habitacionesAdultos != null ? habitacionesAdultos : "No especificado");

        return templateName; // Retorna la plantilla específica del hotel
    }





//    @GetMapping("/")
//    public String vistaHome( ModelMap interfazConPantalla){
//        return "home";
//    }

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
