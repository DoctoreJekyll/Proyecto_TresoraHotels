package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.repositories.HabitacionRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService extends AbstractTemplateServicesEntities<Habitacion, Integer, HabitacionRepo> {

    public HabitacionService(HabitacionRepo habitacionRepo) {
        super(habitacionRepo);
    }

    public Optional<Habitacion> findByIdWithHotelAndProducto(Integer id) {
        return getRepo().findByIdWithHotelAndProducto(id);
    }
}
