package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.repositories.HabitacionRepo;
import com.atm.buenas_practicas_java.repositories.HotelesRepo;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelWebController {

    private final HotelService hotelService;
    private final HabitacionService habitacionService;

    public HotelWebController(HotelService hotelService, HabitacionService habitacionService) {
        this.hotelService = hotelService;
        this.habitacionService = habitacionService;
    }

    @GetMapping("/{nombre}")
    @Transactional(readOnly = true)
    public String mostrarHotelPorNombre(@PathVariable String nombre, Model model) {
        Hotel hotel = hotelService.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel no encontrado"));

        List<Habitacion> habitaciones = habitacionService.findByHotelId(hotel.getId());
        for (Habitacion h : habitaciones) {
            h.getProducto();
            h.getProducto().getPrecioBase();// fuerza inicialización
        }




        model.addAttribute("hotel", hotel);
        model.addAttribute("habitaciones", habitaciones);

        return "hotelesReservas";
    }
}
