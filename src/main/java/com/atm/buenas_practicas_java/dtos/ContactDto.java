package com.atm.buenas_practicas_java.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {
    private String nombre;
    private String email;
    private String phoneNumber;
    private String mensaje;
}
