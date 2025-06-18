package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol idRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel")
    private Hotel idHotel;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 70)
    private String apellidos;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "direccion", length = Integer.MAX_VALUE)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "dni", length = 15)
    private String dni;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Contacto> contactos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<Factura> facturas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<LimpiezaHabitacion> limpiezaHabitacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<Reserva> reservas = new LinkedHashSet<>();

}