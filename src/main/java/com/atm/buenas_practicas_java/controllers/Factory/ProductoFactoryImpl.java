package com.atm.buenas_practicas_java.controllers.Factory;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductoFactoryImpl implements IFactoryProvider{

    private final ProductoService productoService;

    public ProductoFactoryImpl(ProductoService productoService) {
        this.productoService = productoService;
    }


    @Override
    public String getTitles() {
        return "Lista de productos";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id", "idCategoria","idHotel","nombre","descripcion","precioBase","activo","fechaDesde","fechaHasta");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<Producto> productos = productoService.findAll();
        List<Map<String, Object>> rows = productos.stream()
                .map(producto -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", producto.getId());
                    row.put("idCategoria", producto.getIdCategoria().getId());
                    row.put("idHotel", producto.getIdHotel());
                    row.put("nombre", producto.getNombre());
                    row.put("descripcion", producto.getDescripcion());
                    row.put("precioBase", producto.getPrecioBase());
                    row.put("activo", producto.getActivo());
                    row.put("fechaDesde", producto.getFechaDesde());
                    row.put("fechaHasta", producto.getFechaHasta());
                    return row;
                }).toList();

        return rows;
    }

    @Override
    public String getEntityName() {
        return "productos";
    }
}
