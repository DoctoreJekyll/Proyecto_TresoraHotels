package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepo extends JpaRepository<Factura, Integer> {

    @Query("SELECT f FROM Factura f " +
            "JOIN FETCH f.idUsuario " +
            "JOIN FETCH f.idMetodoPago " +
            "JOIN FETCH f.idHotel " +
            "LEFT JOIN FETCH f.detallesFacturas " +
            "WHERE f.id = :id")

    Optional<Factura> findByIdWithAllRelations(@Param("id") Integer id);
}

//JOIN FETCH para forzar carga de Usuario, MetodoPago, Hotel.
// Factura y sus relaciones se carga con una sola query.
//LEFT JOIN FETCH para cargar Set<DetallesFactura> si existen (si no hay, igual trae la factura).
