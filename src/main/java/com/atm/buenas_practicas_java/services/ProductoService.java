package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.repositories.ProductoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepo productoRepo;
    public ProductoService(ProductoRepo productoRepo) {
        this.productoRepo = productoRepo;
    }

    public List<Producto> findAll() {
        return productoRepo.findAll();
    }

    public Optional<Producto> findById(int id) {
        return productoRepo.findById(id);
    }

    public void save(Producto producto) {
        productoRepo.save(producto);
    }

    public void deleteById(int id) {
        productoRepo.deleteById(id);
    }


}
