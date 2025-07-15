package com.atm.buenas_practicas_java.DTOs;

import lombok.Data;

@Data
public class FacturaDto {
    private Integer id;
    private String descripcion;
    private String fecha;
}
