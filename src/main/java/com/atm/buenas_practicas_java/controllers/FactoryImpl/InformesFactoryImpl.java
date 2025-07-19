package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.LimpiezaHabitacion;
import com.atm.buenas_practicas_java.services.InformesService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class InformesFactoryImpl implements IFactoryProvider {

    private final InformesService informesService;

    public InformesFactoryImpl(InformesService informesService) {
        this.informesService = informesService;
    }

    @Override
    public String getTitles() {
        return "Lista de informes";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id", "idUsuario","idHabitacion","fechaLimpieza","horaLimpieza","foto1","foto2");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<LimpiezaHabitacion> limpiezaHabitaciones = informesService.findAll();
        List<Map<String, Object>> rows = limpiezaHabitaciones.stream()
                .map(limpiezaHabitacion -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", limpiezaHabitacion.getId());
                    row.put("idUsuario", limpiezaHabitacion.getIdUsuario().getId());
                    row.put("idHabitacion", limpiezaHabitacion.getIdHabitacion().getId());
                    row.put("fechaLimpieza", limpiezaHabitacion.getFechaLimpieza());
                    row.put("horaLimpieza", limpiezaHabitacion.getHoraLimpieza());
                    row.put("foto1", limpiezaHabitacion.getFoto1());
                    row.put("foto2", limpiezaHabitacion.getFoto2());
                    return row;
                }).toList();

        System.out.println(">>> Hoteles encontrados: " + limpiezaHabitaciones.size()); // TEMPORAL
        return rows;
    }

    @Override
    public String getEntityName() {
        return "informes";
    }
}
