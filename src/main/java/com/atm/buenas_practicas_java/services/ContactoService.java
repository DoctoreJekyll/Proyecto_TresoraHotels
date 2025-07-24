package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.ContactoRepo;
import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import com.atm.buenas_practicas_java.services.files.UploadFilesServiceImpl;
import com.atm.buenas_practicas_java.services.reserva.UsuarioContextService;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContactoService extends AbstractTemplateServicesEntities <Contacto,Integer, ContactoRepo> {

    private final EmailService emailService;
    private final UsuarioContextService usuarioContextService;

    public ContactoService(ContactoRepo repo, EmailService emailService, UsuarioContextService usuarioContextService)
    {
        super(repo);
        this.emailService = emailService;
        this.usuarioContextService = usuarioContextService;
    }

    public Contacto contactoUsuarioLog(UsuarioService usuarioService) {
        Contacto contacto = new Contacto();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow();

            contacto.setNombre(usuario.getNombre());
            contacto.setCorreo(userEmail);
        }
        return contacto;
    }

    public String returnName(UsuarioService usuarioService)
    {
        return usuarioContextService.getNombreUsuarioAutenticado();
    }


    public String returnMail(UsuarioService usuarioService)
    {
        return usuarioContextService.getEmailUsuarioAutenticado();
    }

    public void checkIfImageExistAndCreate(Contacto contacto, MultipartFile file1, MultipartFile file2, ContactoService contactoService, IUploadFilesService uploadFilesService) throws Exception {

        Contacto contactoExistente = null;

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
    }

    //Aqui enviamos el mail
    public void sendEmailWhenContact(Contacto contacto) {
        emailService.sendEmail(
                "notificaciones@agestturnos.es",
                contacto.getCorreo(),
                "Hemos recibido su mensaje correctamente",
                "Hola " + contacto.getNombre() + ",\n\n" +
                        "Hemos recogido su solicitud de contacto\n" +
                        "Gracias por su confianza\n" +
                        "Nos pondremos en contacto lo antes posible \n" +
                        "Su mensaje :" + contacto.getMensaje() + "\n\n" +
                        "¡Esperamos verte pronto!"
        );
    }



}
