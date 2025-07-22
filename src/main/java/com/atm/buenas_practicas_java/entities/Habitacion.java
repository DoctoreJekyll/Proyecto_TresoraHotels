package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "numero_habitacion", nullable = false)
    private Integer numeroHabitacion;

    @Column(name = "piso", nullable = false)
    private Integer piso;

    @Column(name = "tipo", nullable = false, length = 25)
    private String tipo;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "ocupada", nullable = false, length = 30)
    private Boolean ocupada;

    @Column(name = "imagenUrl",nullable = false, length = 30000)
    private String imagenUrl;

    @OneToMany(mappedBy = "idHabitacion")
    private Set<LimpiezaHabitaciones> limpiezaHabitaciones = new LinkedHashSet<>();

    @ManyToMany
    private Set<Producto> productos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idHabitacion")
    private Set<Reserva> reservas = new LinkedHashSet<>();

}