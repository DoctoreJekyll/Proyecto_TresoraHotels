package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habitaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_habitacion", nullable = false)
    private Integer numeroHabitacion;

    @Column(nullable = false)
    private Integer piso;

    public void setNombre(String s) {
    }

    // Otros campos que puedas necesitar como tipo, estado, etc.
}

