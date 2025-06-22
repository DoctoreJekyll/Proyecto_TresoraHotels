package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.repositories.CategoriaProductoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProductoService {
    private final CategoriaProductoRepo productoCatRepo;
    public CategoriaProductoService(CategoriaProductoRepo productoCategoriaRepo) {
        this.productoCatRepo = productoCategoriaRepo;
    }

    public List<CategoriaProducto> findAll() {
        return productoCatRepo.findAll();
    }

    public CategoriaProducto findById(int id) {
        return productoCatRepo.findById(id).get();
    }

    public void save(CategoriaProducto producto) {
        productoCatRepo.save(producto);
    }

    public void deleteById(int id) {
        productoCatRepo.deleteById(id);
    }
}
