package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitacionRepo extends JpaRepository<Habitacion, Integer> {

    @Query("SELECT h FROM Habitacion h "
            + "LEFT JOIN FETCH h.idHotel "
            + "LEFT JOIN FETCH h.idProducto "
            + "WHERE h.id = :id")
    Optional<Habitacion> findByIdWithHotelAndProducto(@Param("id") Integer id);
}
