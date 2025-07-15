package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.ProductosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosUsuarioRepository extends JpaRepository<ProductosUsuario,Integer>{
    List<ProductosUsuario> findByIdReserva_Id(Integer reservaId);
}
