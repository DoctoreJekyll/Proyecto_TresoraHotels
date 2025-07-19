package com.atm.buenas_practicas_java.mappers;

import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioMapper extends AbstractTemplateMapper<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO toDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setEmail(usuario.getEmail());
        dto.setPassword(""); // Nunca devolvemos la real
        dto.setConfirmPassword(""); // No corresponde al modelo
        return dto;
    }

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // 🔐 Recuerda cifrarla más adelante
        usuario.setFechaAlta(LocalDate.now());
        usuario.setActivo(true);
        return usuario;
    }
}
