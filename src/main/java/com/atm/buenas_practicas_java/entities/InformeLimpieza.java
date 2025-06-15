package com.atm.buenas_practicas_java.entities;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "informes_limpieza")
public class InformeLimpieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacion habitacion;
    

    @Column(name = "fecha_limpieza", nullable = false)
    private LocalDate fechaLimpieza;

    @Column(name = "hora_limpieza", nullable = false)
    private LocalTime horaLimpieza;

    @Column(name = "foto1")
    private String foto1;

    @Column(name = "foto2")
    private String foto2;

    public void setUsuario(Usuario juanPérez) {
    }

    public void setHabitacion(Habitacion habitacion) {
    }
}

