package com.atm.buenas_practicas_java.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservaRapidaDTO {
    private String nombre; // si no estás logueado
    private String email;
    private Integer idHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Integer pax;
    private String comentarios;

    // Productos contratados desde el formulario (opcional)
    private List<ProductoFormularioDTO> productos;

}
