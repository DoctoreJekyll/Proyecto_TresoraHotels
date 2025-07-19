package com.atm.buenas_practicas_java.controllers;
import com.atm.buenas_practicas_java.DTOs.LimpiezaHabitacionesDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.mappers.LimpiezaHabitacionesMapper;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.InformesService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/informes")
@AllArgsConstructor
public class InformesController {

    private final InformesService informesService;
    private final UsuarioService usuarioService;
    private final HabitacionService habitacionService;
    private final LimpiezaHabitacionesMapper limpiezaHabitacionesMapper;
    private final IUploadFilesService iUploadFilesService;

    // Mostrar formulario para nuevo informe
    @GetMapping("/nuevo")
    public String mostrarFormularioInforme(Model model) {
        LimpiezaHabitacionesDTO dto = new LimpiezaHabitacionesDTO();
        model.addAttribute("limpiezaHabitacion", dto);

        List<Habitacion> habitaciones = habitacionService.findAllConHotel();

        List<Usuario> employees = getEmployees();

        model.addAttribute("usuarios", employees);
        model.addAttribute("habitaciones", habitaciones);

        return "informeForm";
    }

    private List<Usuario> getEmployees() {
        List<Usuario> allUsers = usuarioService.findAllEntities();
        List<Usuario> employees = new ArrayList<>();

        for (Usuario usuario : allUsers) {
            if (usuario.getIdRol().getNombreRol().equals("LIMPIEZA")) {
                employees.add(usuario);
            }
        }
        return employees;
    }

    // Procesar envío del formulario
    @PostMapping("/guardar")
    public String procesarFormularioInforme(@ModelAttribute("limpiezaHabitacion") LimpiezaHabitacionesDTO dto,
                                            @RequestParam("file1")MultipartFile file1,
                                            @RequestParam("file2")MultipartFile file2) throws Exception {


        if (!file1.isEmpty()) {
            String nombreArchivo1 = iUploadFilesService.handleFileUpload(file1);
            dto.setFoto1("/images/" + nombreArchivo1);
        }
        else
        {
            dto.setFoto1(dto.getFoto1());
        }

        if (!file2.isEmpty()) {
            String nombreArchivo2 = iUploadFilesService.handleFileUpload(file2);
            dto.setFoto2("/images/" + nombreArchivo2);
        }
        else
        {
            dto.setFoto2(dto.getFoto2());
        }

        Usuario usuario = usuarioService.findEntity(dto.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Habitacion habitacion = habitacionService.findByIdWithHotelAndProducto(dto.getIdHabitacion()).orElseThrow();

        LimpiezaHabitacion entidad = limpiezaHabitacionesMapper.toEntity(dto, usuario, habitacion);

        informesService.save(entidad);

        return "redirect:/lista/informes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioInformesEditar(Model model, @PathVariable int id) {
        LimpiezaHabitacion entidad = informesService.findByIdWithHabitacion(id).orElseThrow(() -> new RuntimeException("Habitacion no encontrada"));
        LimpiezaHabitacionesDTO dto = limpiezaHabitacionesMapper.toDTO(entidad);



        model.addAttribute("limpiezaHabitacion", dto);
        model.addAttribute("usuarios", getEmployees());
        model.addAttribute("habitaciones", habitacionService.findAllConHotel());
        return "informeForm";
    }

    @PostMapping("eliminar/{id}")
    public String eliminarInforme(@PathVariable int id) {
        informesService.deleteById(id);
        return "redirect:/lista/informes";
    }
}
