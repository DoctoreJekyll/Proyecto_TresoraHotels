package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.mappers.UsuarioMapper;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntitiesDTOs;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends AbstractTemplateServicesEntitiesDTOs<
        Usuario, UsuarioDTO, Integer, UsuarioRepo, UsuarioMapper> {

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepo repository, UsuarioMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> findByEmail(String email) {
        return this.getRepository().findByEmail(email);
    }

    @Override
    public Usuario saveEntity(Usuario usuario) {
        // Si no está cifrada, la ciframos
        if (!usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return super.saveEntity(usuario);
    }
}
