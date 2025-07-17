package com.atm.buenas_practicas_java.mappers;

import com.atm.buenas_practicas_java.DTOs.LimpiezaHabitacionesDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;

import com.atm.buenas_practicas_java.entities.LimpiezaHabitaciones;
import com.atm.buenas_practicas_java.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class LimpiezaHabitacionesMapper {

    public LimpiezaHabitaciones toEntity(LimpiezaHabitacionesDTO dto, Usuario usuario, Habitacion habitacion) {
        LimpiezaHabitaciones entidad = new LimpiezaHabitaciones();
        entidad.setId(dto.getId());
        entidad.setIdUsuario(usuario);
        entidad.setIdHabitacion(habitacion);
        entidad.setFechaLimpieza(dto.getFechaLimpieza());
        entidad.setHoraLimpieza(dto.getHoraLimpieza());
        entidad.setFoto1(dto.getFoto1());
        entidad.setFoto2(dto.getFoto2());
        return entidad;
    }

    public LimpiezaHabitacionesDTO toDTO(LimpiezaHabitaciones entidad) {
        LimpiezaHabitacionesDTO dto = new LimpiezaHabitacionesDTO();
        dto.setId(entidad.getId());
        dto.setIdUsuario(entidad.getIdUsuario().getId());
        dto.setNombreHotel(entidad.getIdHabitacion().getHotel().getNombre());
        dto.setIdHabitacion(entidad.getIdHabitacion().getId());
        dto.setFechaLimpieza(entidad.getFechaLimpieza());
        dto.setHoraLimpieza(entidad.getHoraLimpieza());
        dto.setFoto1(entidad.getFoto1());
        dto.setFoto2(entidad.getFoto2());
        return dto;
    }
}