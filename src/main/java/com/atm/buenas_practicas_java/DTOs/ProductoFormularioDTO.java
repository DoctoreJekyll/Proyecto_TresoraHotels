package com.atm.buenas_practicas_java.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductoFormularioDTO {
    private Integer idProducto;
    private Integer cantidad;
    private LocalDate fecha;
    private Integer descuento;
}
