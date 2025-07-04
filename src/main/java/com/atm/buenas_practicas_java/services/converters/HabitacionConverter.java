package com.atm.buenas_practicas_java.services.converters;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.services.HabitacionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HabitacionConverter implements Converter <Integer, Habitacion>{

    private final HabitacionService habitacionService;

    public HabitacionConverter(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }


    @Override
    public Habitacion convert(Integer source) {
        return habitacionService.findById(source).orElseThrow();
    }
}
