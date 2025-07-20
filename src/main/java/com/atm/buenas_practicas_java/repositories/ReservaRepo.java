package com.atm.buenas_practicas_java.repositories;


import com.atm.buenas_practicas_java.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepo extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByIdUsuario_Id(Integer idUsuario);

    @Query("SELECT r FROM Reserva r " +
            "JOIN FETCH r.idHabitacion h " +
            "JOIN FETCH h.hotel " +
            "JOIN FETCH r.idUsuario u " +
            "LEFT JOIN FETCH r.productosUsuarios pu " +
            "LEFT JOIN FETCH pu.idProducto " +
            "LEFT JOIN FETCH r.metodoPagoSeleccionado " +
            "WHERE r.id = :id")
    Optional<Reserva> findByIdWithAllRelations(@Param("id") Integer id);

}