package com.atm.buenas_practicas_java.DTOs;

import com.atm.buenas_practicas_java.entities.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelViewDTO {
    private Hotel hotel;
    private double precioMedioHabitaciones;
}
