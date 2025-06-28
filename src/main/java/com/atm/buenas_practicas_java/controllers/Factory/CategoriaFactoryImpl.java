package com.atm.buenas_practicas_java.controllers.Factory;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.services.CategoriaProductoService;
import com.atm.buenas_practicas_java.services.HotelService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoriaFactoryImpl implements IFactoryProvider{

    private final CategoriaProductoService categoriaProductoService;

    public CategoriaFactoryImpl(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }


    @Override
    public String getTitles() {
        return "Lista de categorias";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id", "nombre", "description");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<CategoriaProducto> categoriaProductos = categoriaProductoService.findAll();
        List<Map<String, Object>> rows = categoriaProductos.stream()
                .map(categoriaProducto -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", categoriaProducto.getId());
                    row.put("nombre", categoriaProducto.getNombre());
                    row.put("descripcion", categoriaProducto.getDescripcion());
                    return row;
                }).toList();

        return rows;
    }

    @Override
    public String getEntityName() {
        return "categorias";
    }
}
