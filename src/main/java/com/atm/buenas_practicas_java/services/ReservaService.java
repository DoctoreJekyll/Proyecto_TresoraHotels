package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.ProductoFormularioDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import com.atm.buenas_practicas_java.util.PasswordGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final EmailService emailService;


    public ReservaService(
            ReservaRepo reservaRepository,
            UsuarioRepo usuarioRepository,
            HabitacionRepo habitacionRepository,
            ProductoRepo productoRepository, RolRepo rolRepository, EmailService emailService
    ) {
        super(reservaRepository);
        this.usuarioRepository = usuarioRepository;
        this.habitacionRepository = habitacionRepository;
        this.productoRepository = productoRepository;
        this.rolRepository = rolRepository;
        this.emailService = emailService;
    }

    public Habitacion obtenerHotelPorHabitacionId(Integer idHabitacion) {
        return habitacionRepository.findHabitacionConHotel(idHabitacion);
    }

    public Reserva crearReservaConProductos(ReservaRapidaDTO dto) {
        // 1. Buscar o crear el usuario
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setNombre(dto.getNombre());
                    nuevo.setEmail(dto.getEmail());

                    String rawPassword = PasswordGenerator.generateRandomPassword();
                    String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
                    nuevo.setPassword(encodedPassword);

                    nuevo.setIdRol(rolRepository.getReferenceById(1));
                    return usuarioRepository.save(nuevo);
                });

        Habitacion habitacion = habitacionRepository.findHabitacionConHotel(dto.getIdHabitacion());
        if (habitacion == null) {
            throw new RuntimeException("Habitación no encontrada con el hotel");
        }
        dto.setHotel(habitacion.getHotel().getId()); // Ya viene cargado


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
                if (p.getIdProducto() == null) continue;

                Producto producto = productoRepository.findById(p.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                // FILTRAR POR CATEGORÍA DESDE LA ENTIDAD, NO EL DTO
                if (producto.getIdCategoria().getId() == 2) {
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
            }
            reserva.setProductosUsuarios(productosUsuarios);
        }

        //sendEmail(usuario);

        habitacion.setOcupada(true);
        habitacionRepository.save(habitacion);
        return this.save(reserva);
    }

    private void sendEmail(Usuario usuario)
    {
        emailService.sendEmail(
                "notificaciones@agestturnos.es",
                "jarmar0805@gmail.com",
                "Su usuario temporal",
                "Hemos creado un usuario temporal en nuestra base de datos para que pueda acceder a su perfil y ver su reserva/n" +
                        "Sus datos son " + "User " + usuario.getEmail() + "Contraseña temporal " + usuario.getPassword()
        );
    }
}
