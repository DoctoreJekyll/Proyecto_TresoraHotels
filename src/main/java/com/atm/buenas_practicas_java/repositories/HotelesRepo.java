package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelesRepo extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByNombreIgnoreCase(String nombre);
}
