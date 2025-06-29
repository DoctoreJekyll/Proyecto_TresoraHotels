package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepo extends JpaRepository<Contacto, Integer> {
}
