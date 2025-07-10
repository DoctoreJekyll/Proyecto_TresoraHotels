package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.ProductoFormularioDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ReservaService extends AbstractTemplateServicesEntities<Reserva, Integer, ReservaRepo> {

    private final UsuarioRepo usuarioRepository;
    private final HabitacionRepo habitacionRepository;
    private final ProductoRepo productoRepository;
    private final RolRepo rolRepository;


    public ReservaService(
            ReservaRepo reservaRepository,
            UsuarioRepo usuarioRepository,
            HabitacionRepo habitacionRepository,
            ProductoRepo productoRepository, RolRepo rolRepository
    ) {
        super(reservaRepository);
        this.usuarioRepository = usuarioRepository;
        this.habitacionRepository = habitacionRepository;
        this.productoRepository = productoRepository;
        this.rolRepository = rolRepository;
    }

    public Reserva crearReservaConProductos(ReservaRapidaDTO dto) {

        // 1. Buscar o crear el usuario
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setNombre(dto.getNombre());
                    nuevo.setEmail(dto.getEmail());
                    nuevo.setIdRol(rolRepository.getReferenceById(1));
                    return usuarioRepository.save(nuevo);
                });

        // 2. Obtener la habitación
        Habitacion habitacion = habitacionRepository.findById(dto.getIdHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        // 3. Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(usuario);
        reserva.setIdHabitacion(habitacion);
        reserva.setFechaEntrada(dto.getFechaEntrada());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setPax(dto.getPax());
        reserva.setEstado("Pendiente");
        reserva.setFechaReserva(Instant.now());
        reserva.setComentarios(dto.getComentarios());

        // 4. Procesar productos (si los hay)
        if (dto.getProductos() != null && !dto.getProductos().isEmpty()) {
            Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();

            for (ProductoFormularioDTO p : dto.getProductos()) {
                Producto producto = productoRepository.findById(p.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                ProductosUsuario pu = new ProductosUsuario();
                pu.setIdProducto(producto);
                pu.setIdUsuario(usuario);
                pu.setIdReserva(reserva);
                pu.setCantidad(p.getCantidad());
                pu.setFecha(p.getFecha());
                pu.setDescuento(p.getDescuento());
                pu.setFacturado(false);

                productosUsuarios.add(pu);
            }

            reserva.setProductosUsuarios(productosUsuarios);
        }

        return this.save(reserva);
    }
}
