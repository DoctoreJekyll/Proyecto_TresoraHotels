package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepo extends JpaRepository<Habitacion, Integer> {
}
