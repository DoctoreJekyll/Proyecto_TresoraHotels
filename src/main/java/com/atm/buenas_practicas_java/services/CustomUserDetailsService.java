package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepo usuarioRepo;

    public CustomUserDetailsService(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        String rol = usuario.getIdRol().getNombreRol().toUpperCase(); // Ej: "ADMIN"
        String prefixedRole = "ROLE_" + rol; // Spring Security requiere el prefijo

        return User.builder()
                .username(usuario.getEmail())
                .password("{noop}" + usuario.getPassword()) // Usa {noop} solo para pruebas
                .roles(rol) // O usa .authorities(prefixedRole) si manejas roles manualmente
                .build();
    }
}
