package com.atm.buenas_practicas_java.repositories;



import com.atm.buenas_practicas_java.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ReservaRepo extends JpaRepository<Reserva, Integer> {
}

