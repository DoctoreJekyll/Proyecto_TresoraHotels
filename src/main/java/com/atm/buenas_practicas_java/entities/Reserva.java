package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservas")
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    public enum ESTADO_RESERVA {
        PAGADA,
        CANCELADA,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacion idHabitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Enumerated(EnumType.STRING) // Se guarda como texto
    @Column(name = "estado", nullable = false, length = 30)
    private ESTADO_RESERVA estado = ESTADO_RESERVA.PAGADA;

    @Column(name = "pax", nullable = false)
    private Integer pax;

    @Column(name = "fecha_reserva", nullable = false)
    private Instant fechaReserva;

    @Column(name = "comentarios", length = 80)
    private String comentarios;

    private double totalReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMetodoPagoSeleccionado")
    private MetodoPago metodoPagoSeleccionado;

    @OneToMany(mappedBy = "idReserva")
    private Set<DetallesReserva> detallesReservas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idReserva", cascade = CascadeType.ALL)
    private Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

}

