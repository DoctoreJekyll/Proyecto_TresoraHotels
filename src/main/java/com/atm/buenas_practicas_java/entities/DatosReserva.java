package com.atm.buenas_practicas_java.entities;

public class DatosReserva {
    private String fechaEntrada;
    private String fechaSalida;
    private int adultos;

    // Constructor por defecto
    public DatosReserva() {
        this.adultos = 2; // Valor por defecto
    }

    // Getters y setters
    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getAdultos() {
        return adultos;
    }

    public void setAdultos(int adultos) {
        this.adultos = adultos;
    }
}


