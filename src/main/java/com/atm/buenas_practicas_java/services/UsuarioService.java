package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;

public class UsuarioService extends AbstractTemplateServicesEntities<Usuario, Integer, UsuarioRepo> {
    public UsuarioService(UsuarioRepo repo) {
        super(repo);
    }
}
