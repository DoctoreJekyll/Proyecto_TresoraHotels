package com.atm.buenas_practicas_java.services.converters;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.services.CategoriaProductoService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProductoConverter implements Converter<Integer, CategoriaProducto> {

    private final CategoriaProductoService categoriaProductoService;

    public CategoriaProductoConverter(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @Override
    public CategoriaProducto convert(Integer source) {
        return categoriaProductoService.findById(source).orElseThrow();
    }
}
