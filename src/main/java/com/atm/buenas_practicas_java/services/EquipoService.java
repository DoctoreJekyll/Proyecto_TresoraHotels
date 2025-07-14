package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.MiembroEquipo;
import com.atm.buenas_practicas_java.repositories.MiembroEquipoRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private MiembroEquipoRepo miembroEquipoRepo;

    @Transactional(readOnly = true)
    public List<MiembroEquipo> findAll() {
        List<MiembroEquipo> miembros = miembroEquipoRepo.findAll();
        // Inicializar la colección redesSociales
        miembros.forEach(miembro -> Hibernate.initialize(miembro.getRedesSociales()));
        return miembros;
    }
}