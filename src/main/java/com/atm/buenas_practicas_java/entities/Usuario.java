package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol idRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel")
    private Hotel idHotel;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Column(name = "apellidos", length = 70)
    private String apellidos;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "direccion", length = Integer.MAX_VALUE)
    private String direccion;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @PrePersist
    void alta(){
        this.fechaAlta = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "dni", length = 15)
    private String dni;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Contacto> contactos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL)
    private Set<Factura> facturas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<LimpiezaHabitaciones> limpiezaHabitacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL)
    private Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL)
    private Set<Reserva> reservas = new LinkedHashSet<>();

}