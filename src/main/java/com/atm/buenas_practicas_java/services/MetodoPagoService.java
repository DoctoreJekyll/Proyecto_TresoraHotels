package com.atm.buenas_practicas_java.services;


import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.repositories.MetodoPagoRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

@Service
public class MetodoPagoService extends AbstractTemplateServicesEntities <MetodoPago,Integer, MetodoPagoRepo> {

    public MetodoPagoService(MetodoPagoRepo repo) {
        super(repo);
    }
}
