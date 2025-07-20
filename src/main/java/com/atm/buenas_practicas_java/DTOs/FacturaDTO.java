package com.atm.buenas_practicas_java.DTOs;


import com.atm.buenas_practicas_java.entities.Reserva;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FacturaDTO {
    private Integer id;
    private String email;
    private String nombre;

    private Integer hotel;
    private int numHabitacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEntrada;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision;

    // Productos contratados desde el formulario (opcional)
    private List<ProductoFormularioDTO> productos;
    private String metodoPago;
    private int cantidad;
    private double precioBaseHabitacion;
    private double precioProducto;
    private double precioTotal;
}
