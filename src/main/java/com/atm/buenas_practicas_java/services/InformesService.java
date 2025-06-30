package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.LimpiezaHabitacion;
import com.atm.buenas_practicas_java.repositories.LimpiezaHabitacionesRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class InformesService extends AbstractTemplateServicesEntities<LimpiezaHabitacion, Integer, LimpiezaHabitacionesRepo> {

    public InformesService(LimpiezaHabitacionesRepo repo) {
        super(repo);
    }
}
