package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.idCategoria WHERE p.id = :id")
    Optional<Producto> findByIdWithCategoria(@Param("id") Integer id);

}
