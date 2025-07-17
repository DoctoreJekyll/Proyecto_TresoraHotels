package com.atm.buenas_practicas_java.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class LimpiezaHabitacionesDTO {
    private Integer id; // para edición
    private Integer idUsuario;
    private String nombreHotel;
    private Integer idHabitacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLimpieza;
    private LocalTime horaLimpieza;
    private String foto1;
    private String foto2;

}
