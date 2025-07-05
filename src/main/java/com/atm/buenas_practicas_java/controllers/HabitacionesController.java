package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/habitaciones")
@RequiredArgsConstructor
public class HabitacionesController {

    private final HabitacionService habitacionService;
    private final HotelService hotelService;
    private final ProductoService productoService;

    private final IUploadFilesService  uploadFilesService;


    @GetMapping("/nuevo")
    public String mostrarFormularioHabitaciones(Model model) {
        Habitacion habitacion = new Habitacion();
        model.addAttribute("habitacion", habitacion);

        List<Hotel> hotels = hotelService.findAll();
        model.addAttribute("hoteles", hotels);

        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);

        return "habitacionesForm";
    }

    @PostMapping("/guardar")
    public String guardarHabitacion(@ModelAttribute Habitacion habitacion,
                                    @RequestParam("imagen") MultipartFile imagen,
                                    Model model) throws Exception {

        if (imagen != null && !imagen.isEmpty()) {
            String nombreArchivo = uploadFilesService.handleFileUpload(imagen);
            habitacion.setImagenUrl("/images/" + nombreArchivo);
        }

        habitacionService.save(habitacion);
        return "redirect:/lista/habitaciones";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioHabitacionesEditar(Model model, @PathVariable int id) {
        Habitacion habitacion = habitacionService.findByIdWithHotelAndProducto(id).get();
        model.addAttribute("habitacion", habitacion);

        List<Hotel> hotels = hotelService.findAll();
        model.addAttribute("hoteles", hotels);

        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "habitacionesForm";
    }

    @PostMapping("eliminar/{id}")
    public String eliminarHabitacion(@PathVariable int id) {
        habitacionService.deleteById(id);
        return "redirect:/lista/habitaciones";
    }

}
