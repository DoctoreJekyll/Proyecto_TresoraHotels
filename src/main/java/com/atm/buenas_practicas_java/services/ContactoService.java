package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.ContactoRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContactoService extends AbstractTemplateServicesEntities <Contacto,Integer, ContactoRepo> {

    private EmailService emailService;

    public ContactoService(ContactoRepo repo, EmailService emailService)
    {
        super(repo);
        this.emailService = emailService;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();
            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            return usuario.getNombre();
        }

        return null;
    }


    public String returnMail(UsuarioService usuarioService)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            return user.getUsername();
        }

        return null;
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
