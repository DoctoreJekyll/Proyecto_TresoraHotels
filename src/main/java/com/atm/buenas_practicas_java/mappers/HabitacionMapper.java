package com.atm.buenas_practicas_java.mappers;

import com.atm.buenas_practicas_java.DTOs.HabitacionesDisponiblesDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HabitacionMapper {

    public List<HabitacionesDisponiblesDTO> toDtoList(List<Habitacion> habitaciones) {
        return habitaciones.stream().map(h -> {
            HabitacionesDisponiblesDTO dto = new HabitacionesDisponiblesDTO();
            dto.setId(h.getId());
            dto.setTipo(h.getTipo());
            dto.setCapacidad(h.getCapacidad());
            dto.setImagenUrl(h.getImagenUrl());

            if (h.getProducto() != null) {
                dto.setPrecioBase(h.getProducto().getPrecioBase());
                if (h.getProducto().getIdCategoria() != null) {
                    dto.setCategoriaNombre(h.getProducto().getIdCategoria().getNombre());
                }
            }

            return dto;
        }).collect(Collectors.toList());
    }

}
