package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "contactos")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 70)
    private String correo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "departamento", nullable = false, length = 20)
    private String departamento;

    @Column(name = "mensaje", nullable = false, length = Integer.MAX_VALUE)
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    @PrePersist
    void alta(){
        this.fechaEnvio = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    @Column(name = "foto1", length = 250)
    private String foto1;

    @Column(name = "foto2", length = 250)
    private String foto2;

}