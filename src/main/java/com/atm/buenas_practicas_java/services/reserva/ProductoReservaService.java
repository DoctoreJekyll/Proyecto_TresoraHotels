package com.atm.buenas_practicas_java.services.reserva;

import com.atm.buenas_practicas_java.DTOs.ProductoFormularioDTO;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.entities.ProductosUsuario;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.ProductoService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductoReservaService {
    private static final int CATEGORIA_SERVICIO_ADICIONAL_ID = 2;
    private final ProductoService productoService;

    public ProductoReservaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Convierte una lista de ProductoFormularioDTO en una colección de ProductosUsuario,
     * validando que sean servicios adicionales.
     *
     * @param productoDtos Lista de productos del formulario.
     * @param usuario Usuario asociado a la reserva.
     * @param reserva Reserva a la que se asignarán.
     * @return Conjunto de productos válidos listos para guardar.
     */
    public Set<ProductosUsuario> convertirDTOsAProductos(List<ProductoFormularioDTO> productoDtos, Usuario usuario, Reserva reserva) {
        if (productoDtos == null || productoDtos.isEmpty()) {
            return Collections.emptySet();
        }

        Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

        for (ProductoFormularioDTO pDto : productoDtos) {
            if (pDto.getIdProducto() == null) continue;

            Producto producto = productoService.findById(pDto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + pDto.getIdProducto()));

            if (esServicioAdicional(producto)) {
                ProductosUsuario pu = new ProductosUsuario();
                pu.setIdProducto(producto);
                pu.setIdUsuario(usuario);
                pu.setIdReserva(reserva);
                pu.setCantidad(pDto.getCantidad());
                pu.setFecha(pDto.getFecha());
                pu.setDescuento(pDto.getDescuento());
                pu.setFacturado(false);

                productosUsuarios.add(pu);
            }
        }

        return productosUsuarios;
    }

    private boolean esServicioAdicional(Producto producto) {
        return producto.getIdCategoria() != null &&
                Objects.equals(producto.getIdCategoria().getId(), CATEGORIA_SERVICIO_ADICIONAL_ID);
    }
}
