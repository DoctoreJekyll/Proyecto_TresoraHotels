package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.ContactoRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ContactoService extends AbstractTemplateServicesEntities <Contacto,Integer, ContactoRepo> {
    public ContactoService(ContactoRepo repo) { super(repo); }


    public Contacto contactoUsuarioLog(UsuarioService usuarioService) {
        Contacto contacto = new Contacto();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow();

            contacto.setNombre(usuario.getNombre());
            contacto.setCorreo(userEmail);
        }
        return contacto;
    }

}
