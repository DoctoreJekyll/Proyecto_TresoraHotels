package com.atm.buenas_practicas_java.services.converters;

import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.services.FacturaService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FacturaConverter implements Converter<Integer, Factura> {

    private final FacturaService facturaService;

    public FacturaConverter(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Override
    public Factura convert(Integer source) {
        return facturaService.findByIdWithRelations(source).orElseThrow();
    }
}
