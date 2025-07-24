package com.atm.buenas_practicas_java.services.reserva;

import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioContextService {
    private final UsuarioService usuarioService;

    public UsuarioContextService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Optional<Usuario> getUsuarioAutenticado()
    {
        Usuario usuario = new Usuario();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            return usuarioService.findByEmail(userEmail);
        }
        return Optional.of(usuario);
    }

    public String getNombreUsuarioAutenticado()
    {
        Usuario usuario = getUsuarioAutenticado().orElseThrow();
        return usuario.getNombre();
    }

    public String getEmailUsuarioAutenticado()
    {
        Usuario usuario = getUsuarioAutenticado().orElseThrow();
        return usuario.getEmail();
    }

}
