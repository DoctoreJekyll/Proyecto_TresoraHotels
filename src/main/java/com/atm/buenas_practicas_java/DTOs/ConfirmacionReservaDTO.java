package com.atm.buenas_practicas_java.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ConfirmacionReservaDTO {
    private Integer idReserva;
    private String nombre;
    private String email;
    private String nombreHotel;
    private String habitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Integer pax;
    private String comentarios;
    private double precioBase;

    // Servicios adicionales seleccionados
    private List<ProductoConfirmadoDTO> productos;

    private double precioFinal;


}
