package com.atm.buenas_practicas_java.dtosOld;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;



@Getter
@Setter
public class ReservaDto {
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int Adultos;
}
