package com.atm.buenas_practicas_java.services;


import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.repositories.HotelesRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService extends AbstractTemplateServicesEntities<Hotel, Integer, HotelesRepo> {
    protected HotelService(HotelesRepo hotelesRepo) {
        super(hotelesRepo);
    }

    public Optional<Hotel> findByNombreIgnoreCase(String nombre) {
        return getRepo().findByNombreIgnoreCase(nombre);
    }
}
