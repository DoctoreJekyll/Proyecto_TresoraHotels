package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.services.ContactoService;
import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/contactos")
public class ContactoController {

    private final ContactoService contactoService;
    private final IUploadFilesService uploadFilesService;

    public ContactoController(ContactoService contactoService, IUploadFilesService uploadFilesService) {
        this.contactoService = contactoService;
        this.uploadFilesService = uploadFilesService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioContacto(Model model) {
    Contacto contacto = new Contacto();
    model.addAttribute("contacto", contacto);
    return "contactoForm";
    }

    @PostMapping("/guardar")
    public String guardarContacto(@ModelAttribute Contacto contacto,
                                  @RequestParam(name = "file1") MultipartFile file1,
                                  @RequestParam(name = "file2") MultipartFile file2,
                                  Model model) throws Exception {

        Contacto contactoExistente = null;

        // Solo buscamos si el ID no es nulo
        if (contacto.getId() != null) {
            contactoExistente = contactoService.findById(contacto.getId())
                    .orElse(null); // Evitamos lanzar excepción
        }


        if (!file1.isEmpty()) {
            String nombreArchivo1 = uploadFilesService.handleFileUpload(file1);
            contacto.setFoto1("/images/" + nombreArchivo1);
        }
        else
        {
            if (contactoExistente != null)
            {
               contacto.setFoto1(contacto.getFoto1());
            }
        }

        if (!file2.isEmpty()) {
            String nombreArchivo2 = uploadFilesService.handleFileUpload(file2);
            contacto.setFoto2("/images/" + nombreArchivo2);
        }
        else
        {
            if (contactoExistente != null)
            {
                contacto.setFoto2(contacto.getFoto2());
            }
        }

        contactoService.save(contacto);
        return "redirect:/lista/contactos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioContactoEditar (@PathVariable Integer id, Model model) {
        Contacto contacto = contactoService.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        model.addAttribute("contacto", contacto);
        return "contactoForm";
    }

    @PostMapping("eliminar/{id}")
    public String eliminarContacto (@PathVariable Integer id, Model model) {
        contactoService.deleteById(id);
        return "redirect:/lista/contactos";
    }
}
