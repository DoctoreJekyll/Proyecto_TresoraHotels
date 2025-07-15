package com.atm.buenas_practicas_java.controllers;



import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpSession;

import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelWebController {

    private final HotelService hotelService;
    private final HabitacionService habitacionService;
    private final ProductoService productoService;
    private final ReservaService reservaService;
    private final ReservaConfirmacionMapper confirmacionReservaDTO;


//    @GetMapping("/{nombre}")
//    @Transactional(readOnly = true)
//    public String mostrarHotelPorNombre(@PathVariable String nombre, Model model, Pageable pageable) {
//        Hotel hotel = hotelService.findByNombreIgnoreCase(nombre)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel no encontrado"));
//
//        Page<Habitacion> habitaciones = habitacionService.findByHotelId(hotel.getId(), pageable);
//        for (Habitacion h : habitaciones) {
//            h.getProducto();
//            h.getProducto().getPrecioBase();// fuerza inicialización
//        }
//
//
//        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
//        model.addAttribute("hotel", hotel);
//        model.addAttribute("habitaciones", habitaciones);
//
//        return "hotelesReservas";
@GetMapping("/{nombre}")
@Transactional(readOnly = true)
public String mostrarHotelPorNombre(@PathVariable String nombre, Model model, Pageable pageable, HttpSession session) {
    session.getAttribute("dummy"); // Forzar creación de sesión
    Hotel hotel = hotelService.findByNombreIgnoreCase(nombre)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel no encontrado"));
    System.out.println(hotel.getNombre());
    Page<Habitacion> habitaciones = habitacionService.findByHotelId(hotel.getId(), pageable);
    for (Habitacion h : habitaciones) {
        if (h.getProducto() != null) {
            h.getProducto().getPrecioBase(); // fuerza inicialización
        }
    }

    model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
    model.addAttribute("hotel", hotel);
    model.addAttribute("habitaciones", habitaciones);

    return "hotelesReservas";
    }

    @PostMapping("/reservaCompleta")
    @Transactional
    public String procesarReservaRapida(
            @ModelAttribute("reservaDTO") ReservaRapidaDTO dto,
            Model model
    ) {
        Reserva reservaGuardada = reservaService.crearReservaConProductos(dto);
        ConfirmacionReservaDTO confirmacion = confirmacionReservaDTO.toDto(reservaGuardada, dto);
        long diasEstancia = ChronoUnit.DAYS.between(confirmacion.getFechaEntrada(), confirmacion.getFechaSalida());
        model.addAttribute("diasEstancia", diasEstancia);
        model.addAttribute("reservaConfirmada", confirmacion);
        return "confirmacionReservaRapida";
    }
}
