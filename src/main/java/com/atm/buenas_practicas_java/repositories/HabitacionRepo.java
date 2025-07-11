package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepo extends JpaRepository<Habitacion, Integer> {

    @Query("SELECT h FROM Habitacion h "
            + "LEFT JOIN FETCH h.hotel "
            + "LEFT JOIN FETCH h.producto "
            + "WHERE h.id = :id")
    Optional<Habitacion> findByIdWithHotelAndProducto(@Param("id") Integer id);


    @Query("SELECT h FROM Habitacion h JOIN FETCH h.hotel WHERE h.id = :idHabitacion")
    Habitacion findHabitacionConHotel(@Param("idHabitacion") Integer idHabitacion);

    @Query("SELECT h FROM Habitacion h JOIN FETCH h.hotel")
    List<Habitacion> findAllWithHotel();

}



