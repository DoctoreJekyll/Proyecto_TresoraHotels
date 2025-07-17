package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.LimpiezaHabitaciones;
import com.atm.buenas_practicas_java.repositories.LimpiezaHabitacionesRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InformesService extends AbstractTemplateServicesEntities<LimpiezaHabitaciones, Integer, LimpiezaHabitacionesRepo> {

    public InformesService(LimpiezaHabitacionesRepo repo) {
        super(repo);
    }

    public Optional<LimpiezaHabitaciones> findByIdWithHabitacion(Integer id) {
        return getRepo().findByIdWithHabitacionAndHotel(id);
    }

}
