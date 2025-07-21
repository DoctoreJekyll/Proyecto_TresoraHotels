package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.LimpiezaHabitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LimpiezaHabitacionesRepo extends JpaRepository<LimpiezaHabitaciones, Integer> {

    @Query("SELECT lh FROM LimpiezaHabitaciones lh JOIN FETCH lh.idHabitacion h JOIN FETCH h.hotel WHERE lh.id = :id")
    Optional<LimpiezaHabitaciones> findByIdWithHabitacionAndHotel(@Param("id") Integer id);

}
