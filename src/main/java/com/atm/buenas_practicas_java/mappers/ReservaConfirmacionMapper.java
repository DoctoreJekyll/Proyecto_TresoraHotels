package com.atm.buenas_practicas_java.mappers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.ProductoConfirmadoDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Reserva;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservaConfirmacionMapper {

    @Transactional(readOnly = true)
    public ConfirmacionReservaDTO toDto(Reserva reserva, ReservaRapidaDTO datosFormulario) {
        ConfirmacionReservaDTO dto = new ConfirmacionReservaDTO();

        // Datos desde ReservaRapidaDTO
        dto.setNombre(datosFormulario.getNombre());
        dto.setEmail(datosFormulario.getEmail());

        // Datos desde la entidad persistida
        dto.setIdReserva(reserva.getId());
        dto.setHabitacion(reserva.getIdHabitacion().getTipo());
        dto.setNombreHotel(reserva.getIdHabitacion().getHotel().getNombre());
        dto.setFechaEntrada(reserva.getFechaEntrada());
        dto.setFechaSalida(reserva.getFechaSalida());
        dto.setPax(reserva.getPax());
        dto.setComentarios(reserva.getComentarios());
        dto.setPrecioBase(reserva.getIdHabitacion().getProducto().getPrecioBase());
        dto.setMetodoPagoNombre(reserva.getMetodoPagoSeleccionado().getNombre());


        // Convertir productos contratados
        List<ProductoConfirmadoDTO> productos = reserva.getProductosUsuarios().stream()
                .map(pu -> {
                    ProductoConfirmadoDTO productoDTO = new ProductoConfirmadoDTO();
                    productoDTO.setNombre(pu.getIdProducto().getNombre());
                    productoDTO.setCategoria(pu.getIdProducto().getIdCategoria().getId());
                    productoDTO.setCantidad(pu.getCantidad());
                    productoDTO.setFecha(pu.getFecha());
                    productoDTO.setDescuento(pu.getDescuento());
                    productoDTO.setPrecioBase(pu.getIdProducto().getPrecioBase());
                    return productoDTO;
                })
                .collect(Collectors.toList());

        dto.setProductos(productos);

        double precioProductos = productos.stream()
                .mapToDouble(p -> {
                    double precioConDescuento = p.getPrecioBase() * p.getCantidad();
                    return precioConDescuento;
                })
                .sum();

        // Sumar precio de la habitación también
        long diasEstancia = ChronoUnit.DAYS.between(datosFormulario.getFechaEntrada(), datosFormulario.getFechaSalida());
        double precioEstanciaTotal = reserva.getIdHabitacion().getProducto().getPrecioBase() * diasEstancia;

        dto.setPrecioFinal(precioEstanciaTotal + precioProductos);

        return dto;
    }

}
