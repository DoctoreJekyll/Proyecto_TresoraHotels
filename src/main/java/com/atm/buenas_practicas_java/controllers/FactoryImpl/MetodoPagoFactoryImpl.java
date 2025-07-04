package com.atm.buenas_practicas_java.controllers.FactoryImpl;


import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.services.MetodoPagoService;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MetodoPagoFactoryImpl implements IFactoryProvider {

        private final MetodoPagoService metodoPagoService;

    public MetodoPagoFactoryImpl(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @Override
    public String getTitles() {
        return "Lista de Métodos de Pago";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id, nombre, descripcion");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<MetodoPago> metodoPagos = metodoPagoService.findAll();
        List<Map<String, Object>> rows = metodoPagos.stream()
                .map(metodoPago -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", metodoPago.getId());
                    row.put("nombre", metodoPago.getNombre());
                    row.put("descripcion", metodoPago.getDescripcion());
                    return row;
                }).toList();
        return rows;
    }

    @Override
    public String getEntityName() {
        return "metodoPago";
    }
}
