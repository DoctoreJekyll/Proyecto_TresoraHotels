package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.repositories.ReservaRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Repository;

@Repository
public class ReservaService extends AbstractTemplateServicesEntities<Reserva, Integer, ReservaRepo> {
    public ReservaService(ReservaRepo repo) {
        super(repo);
    }
}
