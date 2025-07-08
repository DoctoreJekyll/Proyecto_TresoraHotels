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

    //Partimos de la base de que nuestra base de dato tenemos que separarla de la app web que de la app del empleado del hotel(que no vamos a tener)
    //Entonces nos sobrarian tablas intermedias que se encargan de registrar cada gasto o cada "consumisiion/servicio" que el huesped hace en el hotel, cosa que no tenemos
    //Por lo tanto teniendo en cuenta que neustra web es principalmente para el usuario que reserva desde web tenemos que generar la factura justo cuando se hace la reserva
    //Y en la reserva se gestiona las cosas que la factura tendra, que sera la hab, sericios, productos incluidos, etc
    //Por tanto hay que cargarse ciertas tablas intermedias como ProductoUsuario o DetalleFactura que se encargaban de gestionar estas cosas y sus campos correspondientes
    //Añadiriamos un campo de tipo Factura a nuestra entidad reserva.

}