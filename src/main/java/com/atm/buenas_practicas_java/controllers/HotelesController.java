package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.LoginDto;
import com.atm.buenas_practicas_java.DTOs.UserDto;
import com.atm.buenas_practicas_java.entities.DatosReserva;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.MiembroEquipo;
import com.atm.buenas_practicas_java.services.EquipoService;
import com.atm.buenas_practicas_java.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;


@Controller
public class HotelesController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private EquipoService equipoService;

    @GetMapping({"/hoteles","", "/", "/home"})
    public String mostrarHoteles(Model model) {
        List<Hotel> hotels = hotelService.findAll();
       // System.out.println("Hoteles obtenidos: " + hotels); // Para depuración
        model.addAttribute("hotels", hotels);
        List<MiembroEquipo> equipo = equipoService.findAll();
     //   System.out.println("Equipo obtenido: " + equipo);
        model.addAttribute("equipo", equipo);
        return "home";

    }

    @Controller
    public class ReservaController {

        @Autowired
        private HotelService hotelService;

        @GetMapping("/reservahome")
        public String mostrarFormularioReserva(
                @RequestParam(value = "id", required = false) String hotelId,
                @RequestParam(value = "hotelId", required = false) String hotelIdFromForm,
                @RequestParam(value = "fechaEntrada", required = false) String fechaEntrada,
                @RequestParam(value = "fechaSalida", required = false) String fechaSalida,
                @RequestParam(value = "adultos", required = false) String adultos,
                Model model) {

            // Unificar hotelId
            String selectedHotelId = hotelId != null ? hotelId : hotelIdFromForm;

            // Crear objeto para el formulario
            DatosReserva datosReserva = new DatosReserva();
            if (fechaEntrada != null && isValidDate(fechaEntrada)) {
                datosReserva.setFechaEntrada(fechaEntrada);
            }
            if (fechaSalida != null && isValidDate(fechaSalida)) {
                datosReserva.setFechaSalida(fechaSalida);
            }
            if (adultos != null && !adultos.trim().isEmpty()) {
                try {
                    int adultosInt = Integer.parseInt(adultos);
                    datosReserva.setAdultos(adultosInt >= 1 && adultosInt <= 4 ? adultosInt : 2);
                } catch (NumberFormatException e) {
                    datosReserva.setAdultos(2);
                }
            } else {
                datosReserva.setAdultos(2);
            }

            // Añadir datos al modelo
            model.addAttribute("datosreserva", datosReserva);
            model.addAttribute("hotelId", selectedHotelId);
            model.addAttribute("hotels", hotelService.findAll());

            // Determinar la plantilla según el hotel seleccionado
            String templateName = "home";
            if (selectedHotelId != null) {
                try {
                    int hotelIdInt = Integer.parseInt(selectedHotelId);
                    if (hotelService.existsById(hotelIdInt)) {
                        Map<Integer, String> hotelTemplates = new HashMap<>();
                        hotelTemplates.put(1, "reservaHotelPlaya");
                        hotelTemplates.put(2, "reservaHotelMontaña");
                        hotelTemplates.put(3, "reservaHotelCiudad");
                        templateName = hotelTemplates.getOrDefault(hotelIdInt, "home");
                    } else {
                        model.addAttribute("error", "El hotel seleccionado no existe");
                    }
                } catch (NumberFormatException e) {
                    model.addAttribute("error", "Identificador de hotel inválido");
                }
            } else {
                model.addAttribute("error", "No se seleccionó un hotel");
            }

            return templateName;
        }

        private boolean isValidDate(String date) {
            try {
                LocalDate.parse(date);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
    }

//    @PostMapping("/reserva")
//    public String procesarReserva(@ModelAttribute("datosreserva") DatosReserva datosReserva,
//                                  @RequestParam("hotelId") String hotelId) {
//        // Lógica para procesar la reserva
//        // Por ejemplo, guardar en la base de datos o redirigir a una página de confirmación
//        return "redirect:/confirmacion";
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

    @PostMapping("/crearCuenta")
    public String postCrearCuenta(@ModelAttribute(name = "userData") UserDto user){
        return "home";
    }

    @GetMapping("/userhome")
    public String mostrarUserHome( ) {
        return "user_home_page";
    }

}
