package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.services.ReservaService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReservaFactoryImpl implements IFactoryProvider {

    private final ReservaService reservaService;

    public ReservaFactoryImpl(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Override
    public String getTitles() {
        return "Lista de reservas";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id","idUsuario","idHabitacion","fechaEntrada","fechaSalida","estado","pax","fechaReserva","comentarios","totalReserva");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getRows() {
        List<Reserva> reservas = reservaService.findAll();
        List<Map<String, Object>> rows = reservas.stream()
                .map(reserva -> {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", reserva.getId());
                    row.put("idUsuario", reserva.getIdUsuario().getId());
                    row.put("idHabitacion", reserva.getIdHabitacion().getTipo());
                    row.put("fechaEntrada", reserva.getFechaEntrada());
                    row.put("fechaSalida", reserva.getFechaSalida());
                    row.put("estado", reserva.getEstado());
                    row.put("pax", reserva.getPax());
                    row.put("fechaReserva", reserva.getFechaReserva());
                    row.put("comentarios", reserva.getComentarios());
                    row.put("totalReserva", reserva.getTotalReserva());
                    System.out.println(reserva.getTotalReserva());
                    return row;
                }).toList();

        return rows;
    }

    @Override
    public String getEntityName() {
        return "reservas";
    }
}
