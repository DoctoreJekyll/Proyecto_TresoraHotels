package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "limpieza_habitaciones")
public class LimpiezaHabitacione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacione idHabitacion;

    @Column(name = "fecha_limpieza", nullable = false)
    private LocalDate fechaLimpieza;

    @Column(name = "hora_limpieza", nullable = false)
    private LocalTime horaLimpieza;

    @Column(name = "foto1")
    private String foto1;

    @Column(name = "foto2")
    private String foto2;

}