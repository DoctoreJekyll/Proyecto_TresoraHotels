package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.repositories.ProductoRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService extends AbstractTemplateServicesEntities<Producto, Integer, ProductoRepo> {

    public ProductoService(ProductoRepo productoRepo) {
        super(productoRepo);
    }

}
