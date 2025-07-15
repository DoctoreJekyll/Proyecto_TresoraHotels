package com.atm.buenas_practicas_java.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitacionesDisponiblesDTO {
    private Integer id;
    private String tipo;
    private Integer capacidad;
    private String imagenUrl;
    private Double precioBase;
    private String categoriaNombre;
}
