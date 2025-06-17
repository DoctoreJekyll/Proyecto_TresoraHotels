package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hoteles")
public class Hotele {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "ciudad", nullable = false, length = 50)
    private String ciudad;

    @Column(name = "direccion", nullable = false, length = 70)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @OneToMany(mappedBy = "idHotel")
    private Set<Factura> facturas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idHotel")
    private Set<Habitacione> habitaciones = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idHotel")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

}