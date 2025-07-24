package com.atm.buenas_practicas_java.services.reserva;

import com.atm.buenas_practicas_java.DTOs.ProductoFormularioDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.entities.Usuario;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateReserva {
    /**
     * Crea una nueva reserva base a partir del DTO.
     */
    public Reserva crearReservaBase(Usuario usuario, Habitacion habitacion, ReservaRapidaDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(usuario);
        reserva.setIdHabitacion(habitacion);
        reserva.setFechaEntrada(dto.getFechaEntrada());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setPax(dto.getPax());
        reserva.setEstado(Reserva.ESTADO_RESERVA.PAGADA); // Esto puede modularizarse si se necesita más flexibilidad
        reserva.setFechaReserva(Instant.now());
        reserva.setComentarios(dto.getComentarios());
        return reserva;
    }

    /**
     * Mapea una entidad Reserva a su DTO correspondiente.
     */
    public ReservaRapidaDTO mapearEntidadADTO(Reserva reserva, List<MetodoPago> metodosPagoDisponibles) {
        ReservaRapidaDTO dto = new ReservaRapidaDTO();

        dto.setIdHabitacion(reserva.getIdHabitacion().getId());
        dto.setHotel(reserva.getIdHabitacion().getHotel().getId());
        dto.setFechaEntrada(reserva.getFechaEntrada());
        dto.setFechaSalida(reserva.getFechaSalida());
        dto.setPax(reserva.getPax());
        dto.setComentarios(reserva.getComentarios());
        dto.setEstado(reserva.getEstado());
        dto.setTotalReserva(reserva.getTotalReserva());

        if (reserva.getIdUsuario() != null) {
            dto.setIdUsuario(reserva.getIdUsuario().getId());
            dto.setNombre(reserva.getIdUsuario().getNombre());
            dto.setEmail(reserva.getIdUsuario().getEmail());
        }

        if (reserva.getProductosUsuarios() != null) {
            List<ProductoFormularioDTO> productos = reserva.getProductosUsuarios().stream()
                    .map(pu -> {
                        ProductoFormularioDTO p = new ProductoFormularioDTO();
                        p.setIdProducto(pu.getIdProducto().getId());
                        p.setCantidad(pu.getCantidad());
                        p.setFecha(pu.getFecha());
                        p.setDescuento(pu.getDescuento());
                        return p;
                    })
                    .collect(Collectors.toList());
            dto.setProductos(productos);
        }

        dto.setMetodoPagos(metodosPagoDisponibles);

        if (reserva.getMetodoPagoSeleccionado() != null) {
            dto.setIdMetodoPagoSeleccionado(reserva.getMetodoPagoSeleccionado().getId());
        }

        return dto;
    }
}
