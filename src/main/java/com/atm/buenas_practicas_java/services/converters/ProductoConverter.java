package com.atm.buenas_practicas_java.services.converters;

import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.services.ProductoService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductoConverter implements Converter<Integer, Producto>
{
    private final ProductoService productoService;
    public ProductoConverter(ProductoService productoService)
    {
        this.productoService = productoService;
    }

    @Override
    public Producto convert(Integer source)
    {
        return productoService.findById(source).orElseThrow();
    }

}
