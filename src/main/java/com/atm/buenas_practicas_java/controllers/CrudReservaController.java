package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.mappers.ReservaConfirmacionMapper;
import com.atm.buenas_practicas_java.services.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class CrudReservaController {

    private final HabitacionService habitacionService;
    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final HotelService hotelService;
    private final ReservaConfirmacionMapper confirmacionReservaDTO;

    @GetMapping("/api/habitaciones")
    @ResponseBody
    public List<Map<String, Object>> getHabitacionesDisponibles(
            @RequestParam("hotelId") Integer hotelId,
            @RequestParam("fechaEntrada") String fechaEntradaStr,
            @RequestParam("fechaSalida") String fechaSalidaStr
    ) {

        LocalDate fechaEntrada = LocalDate.parse(fechaEntradaStr);
        LocalDate fechaSalida = LocalDate.parse(fechaSalidaStr);

        return habitacionService.findDisponiblesPorHotelYFechas(hotelId, fechaEntrada, fechaSalida)
                .stream()
                .map(h -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", h.getId());
                    map.put("tipo", h.getTipo());
                    map.put("precioBase", h.getProducto().getPrecioBase());
                    map.put("numeroHabitacion", h.getNumeroHabitacion());
                    return map;
                })
                .toList();
    }

    @GetMapping("/nuevo")
    public String formReservaNuevo(Model model) {
        List<Habitacion> habitaciones = habitacionService.findAllConHotel();
        Map<Hotel, List<Habitacion>> habitacionesPorHotel = habitaciones.stream()
                .collect(Collectors.groupingBy(Habitacion::getHotel, LinkedHashMap::new, Collectors.toList()));

        ReservaRapidaDTO dto = reservaService.reservaRapidaUsuarioLog(usuarioService);

        model.addAttribute("reservaDTO", dto);
        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("habitacionesPorHotel", habitacionesPorHotel);
        model.addAttribute("hoteles", hotelService.findAll());

        return "reservaForm";
    }

    // 🆗 CREAR o EDITAR usando parámetro "id" externo al DTO
    @PostMapping("/guardar")
    @Transactional
    public String procesarReservaRapida(
            @ModelAttribute("reservaDTO") ReservaRapidaDTO dto,
            @RequestParam(value = "id", required = false) Integer reservaId,
            Model model
    ) {
        Reserva reservaGuardada;

        if (reservaId != null) {
            reservaGuardada = reservaService.actualizarReservaDesdeDTO(reservaId, dto);
        } else {
            reservaGuardada = reservaService.crearReservaConProductos(dto);
        }

        ConfirmacionReservaDTO confirmacion = confirmacionReservaDTO.toDto(reservaGuardada, dto);
        long diasEstancia = ChronoUnit.DAYS.between(confirmacion.getFechaEntrada(), confirmacion.getFechaSalida());
        model.addAttribute("diasEstancia", diasEstancia);
        model.addAttribute("reservaConfirmada", confirmacion);

        return "redirect:/lista/reservas";
    }

    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable Integer id, Model model, HttpSession session) {
        session.getAttribute("dummy");
        Reserva reserva = reservaService.findByIdWithAllRelations(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        ReservaRapidaDTO dto = reservaService.mapearReservaADto(reserva);

        List<Habitacion> habitaciones = habitacionService.findAllConHotel();
        Map<Hotel, List<Habitacion>> habitacionesPorHotel = habitaciones.stream()
                .collect(Collectors.groupingBy(Habitacion::getHotel, LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("reservaDTO", dto);
        model.addAttribute("productos", productoService.obtenerProductosActivosPorCategoria(2));
        model.addAttribute("habitacionesPorHotel", habitacionesPorHotel);
        model.addAttribute("hoteles", hotelService.findAll());
        model.addAttribute("reservaId", id); // solo para construir el action del formulario

        return "reservaForm";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Integer id) {
        reservaService.deleteById(id);
        return "redirect:/lista/reservas";
    }
}
