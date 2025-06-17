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
public class Habitacione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotele idHotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto idProducto;

    @Column(name = "numero_habitacion", nullable = false)
    private Integer numeroHabitacion;

    @Column(name = "piso", nullable = false)
    private Integer piso;

    @Column(name = "tipo", nullable = false, length = 25)
    private String tipo;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "estado_ocupacion", nullable = false, length = 30)
    private String estadoOcupacion;

    @OneToMany(mappedBy = "idHabitacion")
    private Set<DetallesReserva> detallesReservas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idHabitacion")
    private Set<LimpiezaHabitacione> limpiezaHabitaciones = new LinkedHashSet<>();

    @ManyToMany
    private Set<Producto> productos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idHabitacion")
    private Set<Reserva> reservas = new LinkedHashSet<>();

}