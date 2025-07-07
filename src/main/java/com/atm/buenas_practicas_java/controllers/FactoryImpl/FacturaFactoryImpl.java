package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.services.FacturaService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class FacturaFactoryImpl implements IFactoryProvider {

    private final FacturaService facturaService;


    public FacturaFactoryImpl(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Override
    public String getTitles() {
        return "Lista de Facturas";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id","idUsuario", "idMetodoPago","idDetalle","idHotel","fechaEmision","estado","observaciones");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<Factura> facturas = facturaService.findAll();
        List<Map<String, Object>> rows = facturas.stream()
                .map( factura -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", factura.getId());
                    row.put("idUsuario", factura.getIdUsuario().getId());
                    row.put("idMetodoPago", factura.getIdMetodoPago().getId());
                    row.put("idDetalle", factura.getIdDetalle());
                    row.put("idHotel", factura.getIdHotel().getId());
                    row.put("fechaEmision", factura.getFechaEmision());
                    row.put("estado", factura.getEstado());
                    row.put("observaciones", factura.getObservaciones());
                    return row;
                }).toList();
        return rows;
    }

    @Override
    public String getEntityName() {
        return "facturas";
    }

}
