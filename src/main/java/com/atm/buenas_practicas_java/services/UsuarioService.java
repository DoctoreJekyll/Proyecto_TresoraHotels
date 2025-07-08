package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.mappers.UsuarioMapper;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntitiesDTOs;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends AbstractTemplateServicesEntitiesDTOs<
        Usuario, UsuarioDTO, Integer, UsuarioRepo, UsuarioMapper> {

    public UsuarioService(UsuarioRepo repository, UsuarioMapper mapper) {
        super(repository, mapper);
    }

    public Optional<Usuario> findByEmail(String email) {
        return this.getRepository().findByEmail(email);
    }
}
