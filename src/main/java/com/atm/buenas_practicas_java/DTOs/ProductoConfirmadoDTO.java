package com.atm.buenas_practicas_java.DTOs;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductoConfirmadoDTO {
    private String nombre;
    private Integer cantidad;
    private LocalDate fecha;
    private Integer descuento;
    private Integer categoria;
}
