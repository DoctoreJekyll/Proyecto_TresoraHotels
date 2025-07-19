package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaProducto idCategoria;

    @Column(name = "id_hotel", nullable = false)
    private Integer idHotel;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 80)
    private String descripcion;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @OneToMany(mappedBy = "producto")
    private Set<Habitacion> habitaciones = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "productos")
    private Set<Habitacion> habitacionesProducto = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idProducto")
    private Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

}