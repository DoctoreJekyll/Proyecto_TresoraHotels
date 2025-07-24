package com.atm.buenas_practicas_java.services;

// UsuarioService.java
import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Rol;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.mappers.UsuarioMapper;
import com.atm.buenas_practicas_java.repositories.RolRepo;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntitiesDTOs;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsuarioService extends AbstractTemplateServicesEntitiesDTOs<
        Usuario, UsuarioDTO, Integer, UsuarioRepo, UsuarioMapper> {

    private final PasswordEncoder passwordEncoder;
    private final RolRepo rolRepo;

    public UsuarioService(UsuarioRepo repository, UsuarioMapper mapper,
                          PasswordEncoder passwordEncoder, RolRepo rolRepo) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.rolRepo = rolRepo;
    }

    public Optional<Usuario> findByEmail(String email) {
        return getRepository().findByEmail(email);
    }

    public boolean existeEmail(String email) {
        return getRepository().existsByEmail(email);
    }

    public Usuario registrarUsuario(UsuarioDTO dto, Integer rolId, boolean esAdmin) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        if (existeEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setFechaAlta(LocalDate.now());
        usuario.setActivo(true);

        Rol rol;
        if (esAdmin && rolId != null) {
            rol = rolRepo.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no válido"));
        } else {
            rol = rolRepo.findByNombreRol("CLIENTE").orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        }
        usuario.setIdRol(rol);

        return saveEntity(usuario);
    }

    public boolean puedeModificar(Usuario actual, Usuario objetivo) {
        return actual.getEmail().equalsIgnoreCase(objetivo.getEmail())
                || (actual.getIdRol() != null && "ADMIN".equals(actual.getIdRol().getNombreRol()));
    }

    public boolean verificarPassword(Usuario usuario, String rawPassword) {
        return passwordEncoder.matches(rawPassword, usuario.getPassword());
    }

    public void actualizarPassword(Usuario usuario, String nuevaPassword) {
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        saveEntity(usuario);
    }
}

