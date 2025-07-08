package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.services.ContactoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioContacto(Model model) {
    Contacto contacto = new Contacto();
    model.addAttribute("contacto", contacto);
    return "contactForm";
    }

    @PostMapping("/guardar")
    public String guardarContacto(@ModelAttribute Contacto contacto, Model model) {
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
