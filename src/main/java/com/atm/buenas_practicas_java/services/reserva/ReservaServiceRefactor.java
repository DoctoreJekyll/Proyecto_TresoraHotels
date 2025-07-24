package com.atm.buenas_practicas_java.services.reserva;

import com.atm.buenas_practicas_java.DTOs.ReservaRapidaDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.repositories.ReservaRepo;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.MetodoPagoService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservaServiceRefactor extends AbstractTemplateServicesEntities<Reserva, Integer, ReservaRepo> {

    private final HabitacionService habitacionService;
    private final MetodoPagoService metodoPagoService;
    private final UsuarioContextService usuarioContextService;
    private final UsuarioTemporalService usuarioTemporalService;
    private final CreateReserva reservaFactory;
    private final ProductoReservaService productoReservaService;
    private final PrecioReservaCalculadora precioReservaCalculator;
    private final UsuarioRepo usuarioRepo;

    public ReservaServiceRefactor(ReservaRepo repo, HabitacionService habitacionRepo, MetodoPagoService metodoPagoRepo, UsuarioContextService usuarioContextService, UsuarioTemporalService usuarioTemporalService, CreateReserva reservaFactory, ProductoReservaService productoReservaService, PrecioReservaCalculadora precioReservaCalculator, UsuarioRepo usuarioRepo) {
        super(repo);
        this.habitacionService = habitacionRepo;
        this.metodoPagoService = metodoPagoRepo;
        this.usuarioContextService = usuarioContextService;
        this.usuarioTemporalService = usuarioTemporalService;
        this.reservaFactory = reservaFactory;
        this.productoReservaService = productoReservaService;
        this.precioReservaCalculator = precioReservaCalculator;
        this.usuarioRepo = usuarioRepo;
    }

    public List<Reserva> findReservaByUsuario(Integer idUsuario) {
        return getRepo().findByIdUsuario_Id(idUsuario);
    }

    public Habitacion obtenerHotelPorHabitacionId(Integer idHabitacion) {
        return habitacionService.findByIdWithHotelAndProducto(idHabitacion).orElseThrow();
    }

    public Optional<Reserva> findByIdWithAllRelations(Integer id) {
        return getRepo().findByIdWithAllRelations(id);
    }

    @Transactional
    public Reserva crearReservaConProductos(ReservaRapidaDTO dto) {
        Usuario usuario;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Determinar o crear el usuario de la reserva
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();
            usuario = usuarioRepo.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            dto.setEmail(userEmail);
            dto.setNombre(usuario.getNombre());
            dto.setIdUsuario(usuario.getId());
        } else {
            if (dto.getIdUsuario() != null) {
                usuario = usuarioRepo.findById(dto.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            } else {
                usuario = usuarioTemporalService.findOrCreateUser(dto.getNombre(), dto.getEmail());
            }
        }

        Habitacion habitacion = habitacionService.findByIdWithHotelAndProducto(dto.getIdHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada o no asociada a un hotel."));
        dto.setHotel(habitacion.getHotel().getId());

        Reserva reserva = reservaFactory.crearReservaBase(usuario, habitacion, dto);

        Set<ProductosUsuario> productosUsuarios = productoReservaService.convertirDTOsAProductos(dto.getProductos(), usuario, reserva);
        reserva.setProductosUsuarios(productosUsuarios);

        long diasEstancia = ChronoUnit.DAYS.between(dto.getFechaEntrada(), dto.getFechaSalida());
        double precioTotal = precioReservaCalculator.calcularTotal(habitacion, productosUsuarios, diasEstancia);
        reserva.setTotalReserva(precioTotal);
        dto.setTotalReserva(precioTotal);

        MetodoPago metodoPago = metodoPagoService.findById(dto.getIdMetodoPagoSeleccionado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Método de pago no encontrado"));
        reserva.setMetodoPagoSeleccionado(metodoPago);
        reserva.setEstado(Reserva.ESTADO_RESERVA.PAGADA);

        habitacion.setOcupada(true);
        habitacionService.save(habitacion);

        return save(reserva);
    }

    public ReservaRapidaDTO reservaRapidaUsuarioLog(UsuarioService usuarioService) {
        ReservaRapidaDTO dto = new ReservaRapidaDTO();
        usuarioContextService.getUsuarioAutenticado().ifPresent(usuario -> {
            dto.setNombre(usuario.getNombre());
            dto.setEmail(usuario.getEmail());
            dto.setIdUsuario(usuario.getId());
        });
        dto.setMetodoPagos(metodoPagoService.findAll());
        return dto;
    }

    public ReservaRapidaDTO mapearReservaADto(Reserva reserva) {
        List<MetodoPago> metodosPago = metodoPagoService.findAll();
        return reservaFactory.mapearEntidadADTO(reserva, metodosPago);
    }

    @Transactional
    public Reserva actualizarReservaDesdeDTO(Integer id, ReservaRapidaDTO dto) {
        Reserva reserva = getRepo().findByIdWithAllRelations(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Usuario usuario = reserva.getIdUsuario();
        if (usuario == null && dto.getIdUsuario() != null) {
            throw new RuntimeException("La reserva no tiene usuario y no se proporcionó uno.");
        }

        Habitacion habitacion = habitacionService.findByIdWithHotelAndProducto(dto.getIdHabitacion()).orElseThrow();

        reserva.setIdHabitacion(habitacion);
        reserva.setFechaEntrada(dto.getFechaEntrada());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setPax(dto.getPax());
        reserva.setComentarios(dto.getComentarios());
        reserva.setEstado(dto.getEstado());

        reserva.getProductosUsuarios().clear();
        Set<ProductosUsuario> nuevosProductos = productoReservaService.convertirDTOsAProductos(dto.getProductos(), usuario, reserva);
        reserva.getProductosUsuarios().addAll(nuevosProductos);

        long diasEstancia = ChronoUnit.DAYS.between(dto.getFechaEntrada(), dto.getFechaSalida());
        double nuevoTotal = precioReservaCalculator.calcularTotal(habitacion, nuevosProductos, diasEstancia);
        reserva.setTotalReserva(nuevoTotal);

        if (dto.getIdMetodoPagoSeleccionado() != null) {
            MetodoPago metodoPago = metodoPagoService.findById(dto.getIdMetodoPagoSeleccionado())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Método de pago no encontrado"));
            reserva.setMetodoPagoSeleccionado(metodoPago);
        } else {
            reserva.setMetodoPagoSeleccionado(null);
        }

        return save(reserva);
    }

    public String returnName(UsuarioService usuarioService) {
        return usuarioContextService.getNombreUsuarioAutenticado();
    }

    public String returnMail(UsuarioService usuarioService) {
        return usuarioContextService.getEmailUsuarioAutenticado();
    }
}