package com.atm.buenas_practicas_java.entities;

public class DatosReserva {
    private Integer hotelId;
    private String fechaEntrada;
    private String fechaSalida;
    private String habitacionesAdultos;

    // Getters y setters
    public Integer getHotelId() { return hotelId; }
    public void setHotelId(Integer hotelId) { this.hotelId = hotelId; }
    public String getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(String fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public String getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(String fechaSalida) { this.fechaSalida = fechaSalida; }
    public String getHabitacionesAdultos() { return habitacionesAdultos; }
    public void setHabitacionesAdultos(String habitacionesAdultos) { this.habitacionesAdultos = habitacionesAdultos; }
}