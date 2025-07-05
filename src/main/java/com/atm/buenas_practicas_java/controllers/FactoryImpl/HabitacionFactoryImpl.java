package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.services.HabitacionService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class HabitacionFactoryImpl implements IFactoryProvider {

    private final HabitacionService habitacionService;

    public HabitacionFactoryImpl(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @Override
    public String getTitles() {
        return "Lista de habitaciones";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id", "idHotel", "idProducto", "numeroHabitacion", "piso","tipo","capacidad","estado", "imagenUrl");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<Habitacion> habitaciones = habitacionService.findAll();
        List<Map<String, Object>> rows = habitaciones.stream()
                .map(habitacion -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", habitacion.getId());
                    System.out.println(habitacion.getId());
                    row.put("idHotel", habitacion.getIdHotel().getId());
                    System.out.println(habitacion.getIdHotel().getId());
                    row.put("idProducto", habitacion.getIdProducto().getId());
                    System.out.println(habitacion.getIdProducto().getId());
                    row.put("numeroHabitacion", habitacion.getNumeroHabitacion());
                    System.out.println(habitacion.getNumeroHabitacion());
                    row.put("piso", habitacion.getPiso());
                    row.put("tipo", habitacion.getTipo());
                    row.put("capacidad", habitacion.getCapacidad());
                    row.put("estado", habitacion.getEstadoOcupacion());
                    row.put("imagenUrl", habitacion.getImagenUrl());
                    return row;
                }).toList();



        return rows;
    }

    @Override
    public String getEntityName() {
        return "habitaciones";
    }
}
