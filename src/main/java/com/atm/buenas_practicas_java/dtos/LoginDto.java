package com.atm.buenas_practicas_java.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String email;
    private String password;
}
