package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.HabitacionesDisponiblesDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.mappers.HabitacionMapper;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpSession;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelWebController {

    private final HotelService hotelService;
    private final HabitacionService habitacionService;
    private final ProductoService productoService;
    private final ReservaService reservaService;
    private final ReservaConfirmacionMapper confirmacionReservaDTO;
    private final HabitacionMapper habitacionMapper;



    @GetMapping("/api/habitacionesDisponibles")
    @ResponseBody
    public List<HabitacionesDisponiblesDTO> obtenerHabitacionesDisponibles(
            @RequestParam Integer hotelId,
            @RequestParam String fechaEntrada,
            @RequestParam String fechaSalida
    ) {
        List<Habitacion> disponibles = habitacionService.obtenerDisponiblesPorHotelYFechas(hotelId, fechaEntrada, fechaSalida);

        return habitacionMapper.toDtoList(disponibles);
    }


    @GetMapping("/{nombre}")
    @Transactional(readOnly = true)
    public String mostrarHotelPorNombre(
            @PathVariable String nombre,
            @RequestParam(required = false) String fechaEntrada,
            @RequestParam(required = false) String fechaSalida,
            Model model, Pageable pageable, HttpSession session) {

        session.getAttribute("dummy"); // Forzar creación de sesión
        Hotel hotel = hotelService.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel no encontrado"));

        Pageable paginacion = PageRequest.of(pageable.getPageNumber(), 3, Sort.by("id"));
        Page<Habitacion> habitaciones = habitacionService.findByHotelId(hotel.getId(), paginacion);



        for (Habitacion h : habitaciones) {
            if (h.getProducto() != null) {
                h.getProducto().getPrecioBase(); // fuerza inicialización
            }
        }

        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitaciones", habitaciones);

        // Añadir fechas al modelo si vienen en la URL
        model.addAttribute("fechaEntrada", fechaEntrada);
        model.addAttribute("fechaSalida", fechaSalida);

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
