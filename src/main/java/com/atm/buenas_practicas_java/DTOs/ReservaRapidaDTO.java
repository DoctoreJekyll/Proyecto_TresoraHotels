package com.atm.buenas_practicas_java.DTOs;

import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.entities.Reserva;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservaRapidaDTO {
    private Integer hotel;
    private String nombre; // si no estás logueado
    private String email;
    private Integer idHabitacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEntrada;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;
    private Integer pax;
    private String comentarios;
    private Integer idUsuario;

    private Reserva.ESTADO_RESERVA estado;


    private double totalReserva;



    // Productos contratados desde el formulario (opcional)
    private List<ProductoFormularioDTO> productos;

    private List<MetodoPago> metodoPagos;
    private Integer idMetodoPagoSeleccionado;

}
