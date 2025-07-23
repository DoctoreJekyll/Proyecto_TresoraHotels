package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.services.ContactoService;
import com.atm.buenas_practicas_java.services.UsuarioService;
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
    private final UsuarioService usuarioService;

    public ContactoController(ContactoService contactoService, IUploadFilesService uploadFilesService, UsuarioService usuarioService, UsuarioService usuarioService1) {
        this.contactoService = contactoService;
        this.uploadFilesService = uploadFilesService;
        this.usuarioService = usuarioService1;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioContacto(Model model) {
        Contacto contacto = contactoService.contactoUsuarioLog(usuarioService);
        model.addAttribute("contacto", contacto);
        model.addAttribute("usuarioLogeadoEmail", contactoService.returnMail(usuarioService));
        model.addAttribute("usuarioLogeadoName", contactoService.returnName(usuarioService));
        return "contactoForm";
    }

    @PostMapping("/guardar")
    public String guardarContacto(@ModelAttribute Contacto contacto,
                                  @RequestParam("file1") MultipartFile file1,
                                  @RequestParam("file2") MultipartFile file2,
                                  Model model) throws Exception {

        // Servicio para imagenes
        contactoService.checkIfImageExistAndCreate(contacto, file1, file2, contactoService, uploadFilesService);

        //Guardar contacto
        contactoService.save(contacto);

        //Aqui enviamos el mail
        contactoService.sendEmailWhenContact(contacto);

        return "redirect:/contactos";
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
