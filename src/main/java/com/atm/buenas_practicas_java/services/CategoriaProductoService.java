package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.repositories.CategoriaProductoRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProductoService extends AbstractTemplateServicesEntities<CategoriaProducto, Integer, CategoriaProductoRepo> {

    public CategoriaProductoService(CategoriaProductoRepo productoCatRepo) {
        super(productoCatRepo);
    }

}
