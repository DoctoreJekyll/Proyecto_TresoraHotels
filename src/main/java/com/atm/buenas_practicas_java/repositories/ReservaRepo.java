package com.atm.buenas_practicas_java.repositories;


import com.atm.buenas_practicas_java.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepo extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByIdUsuario_Id(Integer idUsuario);
}

