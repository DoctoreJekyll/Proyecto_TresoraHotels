package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Rol;
import com.atm.buenas_practicas_java.repositories.RolRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService extends AbstractTemplateServicesEntities<Rol, Integer, RolRepo> {
    public RolService(RolRepo repo) {
        super(repo);
    }

    public Optional<Rol> findByNombre(String nombre) {
        return this.getRepo().findByNombreRol(nombre);
    }
}

