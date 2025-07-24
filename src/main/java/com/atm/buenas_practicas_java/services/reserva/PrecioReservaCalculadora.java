package com.atm.buenas_practicas_java.services.reserva;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.ProductosUsuario;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PrecioReservaCalculadora {
    /**
     * Calcula el precio total de una reserva sumando el coste de la habitación (por día)
     * y el total de productos adicionales contratados.
     *
     * @param habitacion La habitación reservada.
     * @param productosUsuarios Los productos adicionales asociados.
     * @param diasEstancia Número de días de estancia.
     * @return El precio total de la reserva.
     */
    public double calcularTotal(Habitacion habitacion, Set<ProductosUsuario> productosUsuarios, long diasEstancia) {
        double precioHabitacion = calcularPrecioHabitacion(habitacion, diasEstancia);
        double precioProductos = calcularPrecioProductos(productosUsuarios);
        return precioHabitacion + precioProductos;
    }

    private double calcularPrecioHabitacion(Habitacion habitacion, long diasEstancia) {
        double precioBaseHabitacion = habitacion.getProducto().getPrecioBase();
        return precioBaseHabitacion * diasEstancia;
    }

    private double calcularPrecioProductos(Set<ProductosUsuario> productosUsuarios) {
        return productosUsuarios.stream()
                .mapToDouble(pu -> {
                    double precioUnitario = pu.getIdProducto().getPrecioBase();
                    int cantidad = pu.getCantidad();
                    return precioUnitario * cantidad;
                })
                .sum();
    }
}
