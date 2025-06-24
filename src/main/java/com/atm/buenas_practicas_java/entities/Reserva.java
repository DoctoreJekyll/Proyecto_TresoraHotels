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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
                                        //modificado para meter datos de prueba dataloader!!!!!
    @ManyToOne(fetch = FetchType.LAZY) // optional = false)
    @JoinColumn(name = "id_usuario") //nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacion idHabitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "pax", nullable = false)
    private Integer pax;

    @Column(name = "fecha_reserva", nullable = false)
    private Instant fechaReserva;

    @Column(name = "comentarios", length = 80)
    private String comentarios;

    @OneToMany(mappedBy = "idReserva")
    private Set<DetallesReserva> detallesReservas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idReserva")
    private Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

}