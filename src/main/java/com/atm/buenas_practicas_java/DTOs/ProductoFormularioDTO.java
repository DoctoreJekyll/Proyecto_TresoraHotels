package com.atm.buenas_practicas_java.DTOs;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
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
    private CategoriaProducto categoria;
    private double precioBase;


    private String nombreProducto;
}
