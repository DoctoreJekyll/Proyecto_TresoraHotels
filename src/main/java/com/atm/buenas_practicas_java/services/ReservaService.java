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
import org.springframework.transaction.annotation.Transactional; // Importar Transactional
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

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
            ProductoRepo productoRepository,
            RolRepo rolRepository,
            EmailService emailService
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

    @Transactional // Aseguramos que toda la operación sea transaccional
    public Reserva crearReservaConProductos(ReservaRapidaDTO dto) {
        Usuario usuario;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();
            usuario = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            String userName = usuario.getNombre();

            dto.setEmail(userEmail);
            dto.setNombre(userName);
        }
        else
        {
            if (dto.getIdUsuario() != null)
            {
                usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow();
            }
            else
            {
                usuario = findOrCreateUser(dto.getNombre(), dto.getEmail());
            }
        }

        // 2. Obtener y validar la habitación
        Habitacion habitacion = getAndValidateRoom(dto.getIdHabitacion());
        dto.setHotel(habitacion.getHotel().getId()); // Asegurar que el DTO tiene el ID del hotel

        // 3. Crear la instancia de reserva y establecer atributos básicos
        Reserva reserva = createBaseReservation(usuario, habitacion, dto);

        // 4. Procesar y adjuntar productos adicionales
        Set<ProductosUsuario> productosUsuarios = processAdditionalProducts(dto.getProductos(), usuario, reserva);
        reserva.setProductosUsuarios(productosUsuarios);

        // 5. Guardar la reserva y actualizar el estado de la habitación
        habitacion.setOcupada(true);
        habitacionRepository.save(habitacion);
        Reserva savedReserva = this.save(reserva);

        // 6. Enviar correo (descomentar si se necesita)
        // sendUserCreationEmail(usuario);

        return savedReserva;
    }

    public ReservaRapidaDTO reservaRapidaUsuarioLog(UsuarioService usuarioService) {
        ReservaRapidaDTO dto = new ReservaRapidaDTO(); // Siempre creamos el DTO

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            Usuario usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            dto.setNombre(usuario.getNombre());
            dto.setEmail(userEmail);
            dto.setIdUsuario(usuario.getId());
        }
        return dto;
    }

    /**
     * Busca un usuario por email o crea uno nuevo si no existe.
     * Genera una contraseña aleatoria y la codifica para nuevos usuarios.
     * @param nombre El nombre del usuario.
     * @param email El email del usuario.
     * @return El objeto Usuario existente o recién creado.
     */
    private Usuario findOrCreateUser(String nombre, String email) {
        return usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario newUser = new Usuario();
                    newUser.setNombre(nombre);
                    newUser.setEmail(email);

                    String encodedPassword = PasswordGenerator.generateRandomPassword();
                    newUser.setPassword(encodedPassword);

                    // Asignar el rol por defecto (por ejemplo, 'Cliente' o 'Usuario')
                    Rol defaultRole = rolRepository.findById(1) // Asumiendo que 1 es el ID del rol por defecto
                            .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
                    newUser.setIdRol(defaultRole);

                    // TODO: Considerar enviar el email con la contraseña temporal aquí, o encapsularlo en un evento/listener.
                    // sendUserCreationEmail(newUser, rawPassword); // Pasar la contraseña sin cifrar para el email
                    return usuarioRepository.save(newUser);
                });
    }

    /**
     * Obtiene una habitación por su ID y valida que exista y tenga un hotel asociado.
     * @param idHabitacion El ID de la habitación.
     * @return El objeto Habitacion.
     * @throws RuntimeException Si la habitación no se encuentra o no tiene un hotel.
     */
    private Habitacion getAndValidateRoom(Integer idHabitacion) {
        Habitacion habitacion = habitacionRepository.findHabitacionConHotel(idHabitacion);
        if (habitacion == null || habitacion.getHotel() == null) {
            throw new RuntimeException("Habitación no encontrada o no asociada a un hotel.");
        }
        return habitacion;
    }

    /**
     * Crea una nueva instancia de Reserva con los datos básicos.
     * @param usuario El usuario asociado a la reserva.
     * @param habitacion La habitación reservada.
     * @param dto El DTO con los detalles de la reserva.
     * @return La instancia de Reserva pre-configurada.
     */
    private Reserva createBaseReservation(Usuario usuario, Habitacion habitacion, ReservaRapidaDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(usuario);
        reserva.setIdHabitacion(habitacion);
        reserva.setFechaEntrada(dto.getFechaEntrada());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setPax(dto.getPax());
        reserva.setEstado("Pendiente"); // Estado inicial de la reserva
        reserva.setFechaReserva(Instant.now());
        reserva.setComentarios(dto.getComentarios());
        return reserva;
    }

    /**
     * Procesa la lista de productos adicionales del DTO y los convierte en entidades ProductosUsuario.
     * Solo incluye productos de la categoría especificada (ID 2 en este caso).
     * @param productoDtos La lista de DTOs de productos del formulario.
     * @param usuario El usuario al que se asocian los productos.
     * @param reserva La reserva a la que se asocian los productos.
     * @return Un Set de ProductosUsuario.
     * @throws RuntimeException Si un producto no se encuentra.
     */
    private Set<ProductosUsuario> processAdditionalProducts(List<ProductoFormularioDTO> productoDtos, Usuario usuario, Reserva reserva) {
        if (productoDtos == null || productoDtos.isEmpty()) {
            return Collections.emptySet();
        }

        Set<ProductosUsuario> productosUsuarios = new LinkedHashSet<>();
        Integer CategoriaServicioAdicionalId = 2; // ID de la categoría de servicios adicionales

        for (ProductoFormularioDTO pDto : productoDtos) {
            if (pDto.getIdProducto() == null) {
                continue; // Saltar si no hay ID de producto
            }

            Producto producto = productoRepository.findById(pDto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + pDto.getIdProducto()));

            // Solo añadir productos que pertenecen a la categoría de servicios adicionales (ID 2)
            if (producto.getIdCategoria() != null && producto.getIdCategoria().getId().equals(CategoriaServicioAdicionalId)) {
                ProductosUsuario pu = new ProductosUsuario();
                pu.setIdProducto(producto);
                pu.setIdUsuario(usuario);
                pu.setIdReserva(reserva);
                pu.setCantidad(pDto.getCantidad());
                pu.setFecha(pDto.getFecha());
                pu.setDescuento(pDto.getDescuento());
                pu.setFacturado(false); // Por defecto, no facturado al crear la reserva

                productosUsuarios.add(pu);
            }
        }
        return productosUsuarios;
    }

    /**
     * Envía un correo electrónico al usuario tras su creación.
     * Este método debería idealmente recibir la contraseña sin cifrar si es un email de bienvenida.
     * O mejor aún, un token de restablecimiento de contraseña.
     * @param usuario El usuario al que se le envía el correo.
     */
    private void sendUserCreationEmail(Usuario usuario) {
        // Precaución: `usuario.getPassword()` devolverá la contraseña cifrada.
        // Si necesitas enviar la contraseña "plana", debes pasarla como argumento desde `findOrCreateUser`.
        // Considera implementar un flujo de "establecer contraseña" o "restablecer contraseña" en lugar de enviar la contraseña por email.
        emailService.sendEmail(
                "notificaciones@agestturnos.es",
                usuario.getEmail(), // Enviar al email del usuario real
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