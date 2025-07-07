package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.services.HotelService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HotelFactoryImpl implements IFactoryProvider {

    private final HotelService hotelService;

    public HotelFactoryImpl(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @Override
    public String getTitles() {
        return "Lista de hoteles";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id", "nombre","descripcion","ciudad","direccion","telefono","email");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<Hotel> hotels = hotelService.findAll();
        List<Map<String, Object>> rows = hotels.stream()
                .map(hotel -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", hotel.getId());
                    row.put("nombre", hotel.getNombre());
                    row.put("descripcion", hotel.getDescripcion());
                    row.put("ciudad", hotel.getCiudad());
                    row.put("direccion", hotel.getDireccion());
                    row.put("telefono", hotel.getTelefono());
                    row.put("email", hotel.getEmail());
                    return row;
                }).toList();

        System.out.println(">>> Hoteles encontrados: " + hotels.size()); // TEMPORAL


        return rows;
    }

    @Override
    public String getEntityName() {
        return "hotel";
    }
}
