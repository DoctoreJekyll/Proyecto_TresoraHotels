package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.ProductoFormularioDTO;
import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import com.atm.buenas_practicas_java.util.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReservaService extends AbstractTemplateServicesEntities<Reserva, Integer, ReservaRepo> {

    private final UsuarioRepo usuarioRepository;
    private final HabitacionRepo habitacionRepository;
    private final ProductoRepo productoRepository;
    private final RolRepo rolRepository;
    private final EmailService emailService;
    private final MetodoPagoRepo metodoPagoRepository; // Solo necesitamos este repositorio de los nuevos

    public ReservaService(
            ReservaRepo reservaRepository,
            UsuarioRepo usuarioRepository,
            HabitacionRepo habitacionRepository,
            ProductoRepo productoRepository,
            RolRepo rolRepository,
            EmailService emailService,
            MetodoPagoRepo metodoPagoRepository // Este se mantiene
    ) {
        super(reservaRepository);
        this.usuarioRepository = usuarioRepository;
        this.habitacionRepository = habitacionRepository;
        this.productoRepository = productoRepository;
        this.rolRepository = rolRepository;
        this.emailService = emailService;
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<Reserva> findReservaByUsuario(Integer idUsuario) {
        return getRepo().findByIdUsuario_Id(idUsuario);
    }

    public Habitacion obtenerHotelPorHabitacionId(Integer idHabitacion) {
        return habitacionRepository.findHabitacionConHotel(idHabitacion);
    }

    public Optional<Reserva> findByIdWithAllRelations(Integer id) {
        return getRepo().findByIdWithAllRelations(id);
    }

    /**
     * Crea una nueva reserva con los datos proporcionados en el DTO, incluyendo productos adicionales
     * y el método de pago seleccionado.
     * @param dto El DTO con la información de la reserva.
     * @return La entidad Reserva guardada.
     */
    @Transactional
    public Reserva crearReservaConProductos(ReservaRapidaDTO dto) {
        Usuario usuario;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Determinar o crear el usuario de la reserva
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();
            usuario = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            dto.setEmail(userEmail);
            dto.setNombre(usuario.getNombre());
            dto.setIdUsuario(usuario.getId());
        } else {
            if (dto.getIdUsuario() != null) {
                usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            } else {
                usuario = findOrCreateUser(dto.getNombre(), dto.getEmail());
            }
        }

        // Obtener y validar la habitación
        Habitacion habitacion = getAndValidateRoom(dto.getIdHabitacion());
        dto.setHotel(habitacion.getHotel().getId());

        // Crear la entidad base de la reserva
        Reserva reserva = createBaseReservation(usuario, habitacion, dto);

        // Procesar productos adicionales
        Set<ProductosUsuario> productosUsuarios = processAdditionalProducts(dto.getProductos(), usuario, reserva);
        reserva.setProductosUsuarios(productosUsuarios);

        // Calcular el precio total de la reserva
        long diasEstancia = ChronoUnit.DAYS.between(dto.getFechaEntrada(), dto.getFechaSalida());
        double precioTotalReserva = getPrecioTotalReserva(habitacion, productosUsuarios, diasEstancia);
        reserva.setTotalReserva(precioTotalReserva);
        dto.setTotalReserva(precioTotalReserva);

        // Establecer la habitación como ocupada
        habitacion.setOcupada(true);
        habitacionRepository.save(habitacion);

        // --- Lógica para el método de pago (demo) ---
        if (dto.getIdMetodoPagoSeleccionado() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Se debe seleccionar un método de pago.");
        }
        MetodoPago metodoPagoSeleccionado = metodoPagoRepository.findById(dto.getIdMetodoPagoSeleccionado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Método de pago no encontrado con ID: " + dto.getIdMetodoPagoSeleccionado()));
        reserva.setMetodoPagoSeleccionado(metodoPagoSeleccionado);
        // En una demo, asumimos que el pago es exitoso
        reserva.setEstado(Reserva.ESTADO_RESERVA.PAGADA);
        // ---------------------------------------------

        Reserva savedReserva = this.save(reserva);
        return savedReserva;
    }

    private static double getPrecioTotalReserva(Habitacion habitacion, Set<ProductosUsuario> productosUsuarios, long diasEstancia) {
        double precioBaseHabitacion = habitacion.getProducto().getPrecioBase();
        double precioProductosTotal = 0.0;

        for (ProductosUsuario pu : productosUsuarios) {
            double precioUnitario = pu.getIdProducto().getPrecioBase();
            int cantidad = pu.getCantidad();
            precioProductosTotal += precioUnitario * cantidad;
        }

        double precioHabitacionTotal = precioBaseHabitacion * diasEstancia;
        return precioProductosTotal + precioHabitacionTotal;
    }

    /**
     * Prepara un DTO de reserva rápida para un usuario logueado, incluyendo los métodos de pago disponibles.
     * @param usuarioService Servicio de usuario para obtener detalles del usuario.
     * @return Un ReservaRapidaDTO con los datos del usuario logueado y métodos de pago.
     */
    public ReservaRapidaDTO reservaRapidaUsuarioLog(UsuarioService usuarioService) {
        ReservaRapidaDTO dto = new ReservaRapidaDTO();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            dto.setNombre(usuario.getNombre());
            dto.setEmail(userEmail);
            dto.setIdUsuario(usuario.getId());
        }

        // Cargar y añadir los métodos de pago disponibles al DTO
        List<MetodoPago> metodosPagoDisponibles = metodoPagoRepository.findAll();
        dto.setMetodoPagos(metodosPagoDisponibles);

        return dto;
    }

    private Usuario findOrCreateUser(String nombre, String email) {
        return usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario newUser = new Usuario();
                    newUser.setNombre(nombre);
                    newUser.setEmail(email);

                    String encodedPassword = PasswordGenerator.generateRandomPassword();
                    newUser.setPassword(encodedPassword);

                    Rol defaultRole = rolRepository.findById(1)
                            .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
                    newUser.setIdRol(defaultRole);

                    return usuarioRepository.save(newUser);
                });
    }

    private Habitacion getAndValidateRoom(Integer idHabitacion) {
        Habitacion habitacion = habitacionRepository.findHabitacionConHotel(idHabitacion);
        if (habitacion == null || habitacion.getHotel() == null) {
            throw new RuntimeException("Habitación no encontrada o no asociada a un hotel.");
        }
        return habitacion;
    }

    private Reserva createBaseReservation(Usuario usuario, Habitacion habitacion, ReservaRapidaDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(usuario);
        reserva.setIdHabitacion(habitacion);
        reserva.setFechaEntrada(dto.getFechaEntrada());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setPax(dto.getPax());
        reserva.setEstado(Reserva.ESTADO_RESERVA.PAGADA);
        reserva.setFechaReserva(Instant.now());
        reserva.setComentarios(dto.getComentarios());
        return reserva;
    }

    private Set<ProductosUsuario> processAdditionalProducts(List<ProductoFormularioDTO> productoDtos, Usuario usuario, Reserva reserva) {
        if (productoDtos == null || productoDtos.isEmpty()) {
            return Collections.emptySet();
        }

        Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();
        Integer CategoriaServicioAdicionalId = 2;

        for (ProductoFormularioDTO pDto : productoDtos) {
            if (pDto.getIdProducto() == null) {
                continue;
            }

            Producto producto = productoRepository.findById(pDto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + pDto.getIdProducto()));

            if (producto.getIdCategoria() != null && producto.getIdCategoria().getId().equals(CategoriaServicioAdicionalId)) {
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

    /**
     * Mapea una entidad Reserva a un DTO de ReservaRapidaDTO.
     * Incluye la lógica para añadir los métodos de pago disponibles al DTO y pre-seleccionar el elegido.
     * @param reserva La entidad Reserva a mapear.
     * @return El ReservaRapidaDTO resultante.
     */
    public ReservaRapidaDTO mapearReservaADto(Reserva reserva) {
        ReservaRapidaDTO dto = new ReservaRapidaDTO();

        dto.setIdHabitacion(reserva.getIdHabitacion().getId());
        dto.setHotel(reserva.getIdHabitacion().getHotel().getId());
        dto.setFechaEntrada(reserva.getFechaEntrada());
        dto.setFechaSalida(reserva.getFechaSalida());
        dto.setPax(reserva.getPax());
        dto.setComentarios(reserva.getComentarios());
        dto.setEstado(reserva.getEstado());

        if (reserva.getIdUsuario() != null) {
            dto.setIdUsuario(reserva.getIdUsuario().getId());
            dto.setNombre(reserva.getIdUsuario().getNombre());
            dto.setEmail(reserva.getIdUsuario().getEmail());
        }

        if (reserva.getProductosUsuarios() != null) {
            List<ProductoFormularioDTO> productos = reserva.getProductosUsuarios().stream().map(pu -> {
                ProductoFormularioDTO p = new ProductoFormularioDTO();
                p.setIdProducto(pu.getIdProducto().getId());
                p.setCantidad(pu.getCantidad());
                p.setFecha(pu.getFecha());
                p.setDescuento(pu.getDescuento());
                return p;
            }).toList();
            dto.setProductos(productos);
        }

        dto.setTotalReserva(reserva.getTotalReserva());

        // Cargar y añadir los métodos de pago disponibles al DTO para mostrar en el formulario
        List<MetodoPago> metodosPagoDisponibles = metodoPagoRepository.findAll();
        dto.setMetodoPagos(metodosPagoDisponibles);

        // Si la reserva ya tiene un método de pago seleccionado, lo pre-selecciona en el DTO
        if (reserva.getMetodoPagoSeleccionado() != null) {
            dto.setIdMetodoPagoSeleccionado(reserva.getMetodoPagoSeleccionado().getId());
        }

        return dto;
    }

    /**
     * Actualiza una reserva existente con los datos proporcionados en el DTO.
     * También actualiza el método de pago seleccionado.
     * @param id El ID de la reserva a actualizar.
     * @param dto El DTO con la información actualizada de la reserva.
     * @return La entidad Reserva actualizada.
     */
    @Transactional
    public Reserva actualizarReservaDesdeDTO(Integer id, ReservaRapidaDTO dto) {
        Reserva reserva = getRepo().findByIdWithAllRelations(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Usuario usuario = reserva.getIdUsuario();
        if (usuario == null && dto.getIdUsuario() != null) {
            usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            reserva.setIdUsuario(usuario);
        } else if (usuario == null) {
            throw new RuntimeException("La reserva no tiene un usuario asociado y no se proporcionó uno en el DTO.");
        }

        Habitacion nuevaHabitacion = getAndValidateRoom(dto.getIdHabitacion());
        reserva.setIdHabitacion(nuevaHabitacion);

        reserva.setFechaEntrada(dto.getFechaEntrada());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setPax(dto.getPax());
        reserva.setComentarios(dto.getComentarios());
        reserva.setEstado(dto.getEstado());

        reserva.getProductosUsuarios().clear();
        Set<ProductosUsuario> nuevosProductos = processAdditionalProducts(dto.getProductos(), usuario, reserva);
        reserva.getProductosUsuarios().addAll(nuevosProductos);

        long diasEstancia = ChronoUnit.DAYS.between(dto.getFechaEntrada(), dto.getFechaSalida());
        double nuevoTotal = getPrecioTotalReserva(nuevaHabitacion, nuevosProductos, diasEstancia);
        reserva.setTotalReserva(nuevoTotal);

        if (dto.getIdMetodoPagoSeleccionado() != null) {
            MetodoPago metodoPagoSeleccionado = metodoPagoRepository.findById(dto.getIdMetodoPagoSeleccionado())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Método de pago no encontrado con ID: " + dto.getIdMetodoPagoSeleccionado()));
            reserva.setMetodoPagoSeleccionado(metodoPagoSeleccionado);
        } else {
            reserva.setMetodoPagoSeleccionado(null);
        }

        return getRepo().save(reserva);
    }

    public String returnName(UsuarioService usuarioService)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();
            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            return usuario.getNombre();
        }

        return null;
    }

    public String returnMail(UsuarioService usuarioService)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails)  {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            return user.getUsername();
        }

        return null;
    }


    private void sendUserCreationEmail(Usuario usuario) {
        emailService.sendEmail(
                "notificaciones@agestturnos.es",
                usuario.getEmail(),
                "¡Bienvenido a nuestro hotel! Tu acceso temporal",
                "Hola " + usuario.getNombre() + ",\n\n" +
                        "Hemos creado un usuario temporal en nuestra base de datos para que puedas acceder a tu perfil y ver tu reserva.\n" +
                        "Tus datos de acceso son:\n" +
                        "Usuario (Email): " + usuario.getEmail() + "\n" +
                        "Contraseña temporal: [CONTRASEÑA_TEMPORAL_AQUI - NO ENVIAR CIFRADA]\n\n" +
                        "Por favor, cambia tu contraseña al iniciar sesión por primera vez.\n\n" +
                        "¡Esperamos verte pronto!"
        );
    }
}