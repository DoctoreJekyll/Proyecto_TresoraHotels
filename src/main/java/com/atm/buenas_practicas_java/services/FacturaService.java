package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.repositories.FacturaRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacturaService extends AbstractTemplateServicesEntities <Factura, Integer,FacturaRepo> {
    public FacturaService(FacturaRepo repo) {
        super(repo);
    }

    public Optional<Factura> findByIdWithRelations(Integer id) {
        return getRepo().findByIdWithAllRelations(id);
    }
}