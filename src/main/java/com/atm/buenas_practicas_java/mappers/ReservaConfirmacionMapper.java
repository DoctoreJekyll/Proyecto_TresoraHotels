package com.atm.buenas_practicas_java.mappers;

import com.atm.buenas_practicas_java.DTOs.ConfirmacionReservaDTO;
import com.atm.buenas_practicas_java.DTOs.ProductoConfirmadoDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservaConfirmacionMapper {

    @Transactional
    public ConfirmacionReservaDTO toDto(Reserva reserva, ReservaRapidaDTO datosFormulario) {
        ConfirmacionReservaDTO dto = new ConfirmacionReservaDTO();

        // Datos desde ReservaRapidaDTO
        dto.setNombre(datosFormulario.getNombre());
        dto.setEmail(datosFormulario.getEmail());

        // Datos desde la entidad persistida
        dto.setIdReserva(reserva.getId());
        dto.setHabitacion(reserva.getIdHabitacion().getTipo());
        dto.setFechaEntrada(reserva.getFechaEntrada());
        dto.setFechaSalida(reserva.getFechaSalida());
        dto.setPax(reserva.getPax());
        dto.setComentarios(reserva.getComentarios());

        // Convertir productos contratados
        List<ProductoConfirmadoDTO> productos = reserva.getProductosUsuarios().stream()
                .map(pu -> {
                    ProductoConfirmadoDTO productoDTO = new ProductoConfirmadoDTO();
                    productoDTO.setNombre(pu.getIdProducto().getNombre());
                    productoDTO.setCategoria(pu.getIdProducto().getIdCategoria().getId());
                    productoDTO.setCantidad(pu.getCantidad());
                    productoDTO.setFecha(pu.getFecha());
                    productoDTO.setDescuento(pu.getDescuento());
                    return productoDTO;
                })
                .collect(Collectors.toList());

        dto.setProductos(productos);

        return dto;
    }
}
