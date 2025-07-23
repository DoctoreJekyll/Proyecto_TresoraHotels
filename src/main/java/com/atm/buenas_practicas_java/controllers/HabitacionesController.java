package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/habitaciones")
@RequiredArgsConstructor
@Slf4j
public class HabitacionesController {

    private final HabitacionService habitacionService;
    private final HotelService hotelService;
    private final ProductoService productoService;
    private final IUploadFilesService uploadFilesService;

    @GetMapping("/nuevo")
    public String mostrarFormularioHabitaciones(Model model) {
        model.addAttribute("habitacion", new Habitacion());
        model.addAttribute("hoteles", hotelService.findAll());
        model.addAttribute("productos", productoService.findAll());
        return "habitacionesForm";
    }

    @PostMapping("/guardar")
    public String guardarHabitacion(@ModelAttribute Habitacion habitacion,
                                    @RequestParam("imagen") MultipartFile imagen,
                                    Model model) throws Exception {
        log.info("📥 Guardando habitación: {}", habitacion.getTipo());

        if (!imagen.isEmpty()) {
            log.debug("🖼 Imagen recibida: {}", imagen.getOriginalFilename());
            String nombreArchivo = uploadFilesService.handleFileUpload(imagen);

            if (nombreArchivo == null || nombreArchivo.contains("Upload File Too Large") || nombreArchivo.contains("Only jpg")) {
                log.warn("⚠️ Problema al subir imagen: {}", nombreArchivo);
                model.addAttribute("errorImagen", nombreArchivo);
                model.addAttribute("habitacion", habitacion);
                model.addAttribute("hoteles", hotelService.findAll());
                model.addAttribute("productos", productoService.findAll());
                return "habitacionesForm";
            }

            habitacion.setImagenUrl("/images/" + nombreArchivo);
            log.info("✅ Imagen asociada a habitación: {}", habitacion.getImagenUrl());

        } else {
            log.debug("📂 No se subió imagen nueva, recuperando la actual desde BD");
            habitacionService.findById(habitacion.getId()).ifPresent(h -> {
                habitacion.setImagenUrl(h.getImagenUrl());
                log.debug("🔁 Imagen existente mantenida: {}", h.getImagenUrl());
            });
        }

        habitacionService.save(habitacion);
        log.info("✅ Habitación guardada correctamente: {}", habitacion.getTipo());

        return "redirect:/lista/habitaciones";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioHabitacionesEditar(Model model, @PathVariable int id) {
        Habitacion habitacion = habitacionService.findByIdWithHotelAndProducto(id).orElseThrow();
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("hoteles", hotelService.findAll());
        model.addAttribute("productos", productoService.findAll());
        return "habitacionesForm";
    }

    @PostMapping("eliminar/{id}")
    public String eliminarHabitacion(@PathVariable int id) {
        habitacionService.deleteById(id);
        return "redirect:/lista/habitaciones";
    }
}
