package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.repositories.ContactoRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

@Service
public class ContactoService extends AbstractTemplateServicesEntities <Contacto,Integer, ContactoRepo> {
    public ContactoService(ContactoRepo repo) { super(repo); }
}
