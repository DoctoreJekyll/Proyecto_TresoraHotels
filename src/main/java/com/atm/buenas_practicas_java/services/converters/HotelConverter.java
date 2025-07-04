package com.atm.buenas_practicas_java.services.converters;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.services.HotelService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HotelConverter implements Converter<Integer, Hotel> {
    private final HotelService hotelService;
    public HotelConverter(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public Hotel convert(Integer source) {
        return hotelService.findById(source).orElseThrow();
    }
}
