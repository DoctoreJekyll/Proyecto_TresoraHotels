package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.entities.*;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.repositories.EntidadHijaRepository;
import com.atm.buenas_practicas_java.repositories.EntidadPadreRepository;
import com.atm.buenas_practicas_java.repositories.HabitacionRepo;
import com.atm.buenas_practicas_java.repositories.HotelesRepo;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.repositories.ReservaRepo;
import com.atm.buenas_practicas_java.entities.Usuario;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.time.LocalDate;


/**
 * Clase de configuración que se utiliza exclusivamente para el perfil "default" en entornos locales.
 *
 * Su propósito principal es cargar datos de ejemplo en las bases de datos asociadas a las entidades
 * {@code EntidadPadre} y {@code EntidadHija}, permitiendo la inicialización de datos útiles para pruebas
 * y desarrollo en este perfil específico.
 *
 * Esta clase está anotada con:
 * - {@code @Configuration}: Define esta clase como fuente de beans y configuración.
 * - {@code @Log4j2}: Habilita el uso de la biblioteca Log4j2 para registro de mensajes en los logs.
 * - {@code @Profile("default")}: Asegura que esta clase solo se cargue en el perfil "default".
 *
 * @see EntidadPadreRepository
 * @see EntidadHijaRepository
 */
@Configuration
@Log4j2
@Profile("local")
public class LoaderReservaPrueba {
    /*public class LocalDataLoader {*/
    private final HotelesRepo hotelesRepo;
    private final HabitacionRepo habitacionRepo;
    private final UsuarioRepo usuarioRepo;
    private final RolRepo rolRepo;
    private final ReservaRepo reservaRepo;

    /**
     * Constructor de la clase {@code LocalDataLoader}.
     * <p>
     * Inicializa un objeto {@code LocalDataLoader} configurado con los repositorios de las entidades,
     * proporcionando la capacidad de interactuar con estas entidades en la base de datos.
     */
    /* public LocalDataLoader(HotelesRepo  hotelesRepo,  HabitacionRepo habitacionRepo) {*/
    public LoaderReservaPrueba(HotelesRepo hotelesRepo, HabitacionRepo habitacionRepo, UsuarioRepo usuarioRepo, RolRepo rolRepo, ReservaRepo reservaRepo) {
        this.hotelesRepo = hotelesRepo;
        this.habitacionRepo = habitacionRepo;
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.reservaRepo = reservaRepo;
    }

    /**
     * Método anotado con {@code @PostConstruct} que inicializa datos de prueba en
     * los repositorios para entornos locales. Este método se ejecuta automáticamente
     * después de la inicialización del bean y antes de que esté disponible para uso,
     * permitiendo cargar datos iniciales necesarios para el perfil local.
     * <p>
     * Funcionalidad:
     * - Crea 10 instancias de la entidad `EntidadPadre` con nombres predefinidos.
     * - Guarda las instancias de `EntidadPadre` en el repositorio correspondiente.
     * - Para cada instancia de `EntidadPadre`, crea una entidad relacionada de tipo
     * `EntidadHija` con un nombre identificativo, y la asocia a la entidad padre
     * pertinente.
     * - Guarda las entidades hijas en el repositorio `entidadHijaRepository`.
     * - Registra mensajes informativos en el log sobre el inicio y finalización del proceso.
     * <p>
     * Proceso:
     * 1. Se define un número fijo de entidades padre (10).
     * 2. Se utiliza un array para almacenar las instancias y se inicializa con un nombre
     * único para cada entidad padre.
     * 3. Todas las entidades padre se guardan de forma simultánea utilizando
     * {@code repository.saveAll}.
     * 4. Para cada entidad padre, se crea una instancia de la entidad hija, se establece
     * la relación con el padre y se guarda en el repositorio correspondiente.
     * 5. Se registran logs informativos sobre el estado del proceso.
     * <p>
     * Dependencias principales:
     * - `repository`: {@code EntidadPadreRepository}, usado para almacenar las entidades padre.
     * - `entidadHijaRepository`: {@code EntidadHijaRepository}, usado para guardar las entidades hijas.
     * <p>
     * Importante:
     * - Este método está diseñado específicamente para ser utilizado en entornos con
     * el perfil local activo.
     * - No debe usarse en entornos de producción, ya que sobrescribirá datos existentes.
     * <p>
     * Logs:
     * - Mensaje al inicio del proceso: "Iniciando la carga de datos para el perfil local".
     * - Mensaje exitoso al finalizar: "Datos de entidades cargados correctamente."
     */

    @PostConstruct
    public void loadDataDesarrollo() {
        loadHoteles();
        loadRoles();
        loadUsuarios();
        loadReservas();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void loadHoteles() {
        log.info("Iniciando carga de hoteles ficticios...");

        SaveAllHoteles();
        SaveAllHabitaciones();

        log.info("Carga completada");
    }
    private Hotel hotel1;
    private Hotel hotel2;
    private Hotel hotel3;

    private void SaveAllHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();

        Habitacion habitacion = habitacion1();
        Habitacion habitacion2 = habitacion2();
        Habitacion habitacion3 = habitacion3();
        habitaciones.add(habitacion);
        habitaciones.add(habitacion2);
        habitaciones.add(habitacion3);

        habitacionRepo.saveAll(habitaciones);
    }

    private void SaveAllHoteles() {
        List<Hotel> hoteles = new ArrayList<>();

        hotel1 = getHotel();
        hoteles.add(hotel1);

        hotel2 = getHotel2();
        hoteles.add(hotel2);

        hotel3 = getHotel3();
        hoteles.add(hotel3);

        hotelesRepo.saveAll(hoteles);
    }

    private Hotel getHotel3() {
        Hotel hotel3 = new Hotel();
        hotel3.setNombre("Costa Azul Resort");
        hotel3.setDescripcion("Relájate frente al mar Mediterráneo");
        hotel3.setCiudad("Alicante");
        hotel3.setDireccion("Avenida del Mar, 55, 03001 Alicante");
        hotel3.setDireccionURL("https://maps.google.com/?q=Avenida+del+Mar+55+Alicante");
        hotel3.setImageURL("https://picsum.photos/600/400?random=12");
        hotel3.setTelefono("+34 611 555 666");
        hotel3.setEmail("contacto@costazulresort.com");
        return hotel3;
    }

    private Hotel getHotel2() {
        Hotel hotel2 = new Hotel();
        hotel2.setNombre("Monteverde Palace");
        hotel2.setDescripcion("Lujo entre montañas y aire puro");
        hotel2.setCiudad("Granada");
        hotel2.setDireccion("Camino de la Sierra, s/n, 18010, Granada");
        hotel2.setDireccionURL("https://maps.google.com/?q=Camino+de+la+Sierra+Granada");
        hotel2.setImageURL("https://picsum.photos/600/400?random=8");
        hotel2.setTelefono("+34 622 333 444");
        hotel2.setEmail("reservas@monteverdepalace.com");
        return hotel2;
    }

    private Hotel getHotel() {
        Hotel hotel1 = new Hotel();
        hotel1.setNombre("Tresora Beach");
        hotel1.setDescripcion("Un paraíso costero para que te lo goces");
        hotel1.setCiudad("Málaga");
        hotel1.setDireccion("Playa de Pedregalejo, Málaga-Este, 29017, Málaga");
        hotel1.setDireccionURL("https://maps.google.com/?q=Playa+de+Pedregalejo");
        hotel1.setImageURL("https://picsum.photos/600/400?random=5");
        hotel1.setTelefono("+34 633 111 222");
        hotel1.setEmail("info@tresorabeach.com");
        return hotel1;
    }

    private Habitacion habitacion1() {
        Habitacion habitacion1 = new Habitacion();
        habitacion1.setIdHotel(hotel1);
        habitacion1.setIdProducto(null);
        habitacion1.setNumeroHabitacion(0);
        habitacion1.setPiso(0);
        habitacion1.setTipo("Individual");
        habitacion1.setCapacidad(1);
        habitacion1.setEstadoOcupacion("Libre");
        return habitacion1;
    }

    private Habitacion habitacion2() {
        Habitacion habitacion2 = new Habitacion();
        habitacion2.setIdHotel(hotel2);
        habitacion2.setIdProducto(null);
        habitacion2.setNumeroHabitacion(101);
        habitacion2.setPiso(1);
        habitacion2.setTipo("Doble");
        habitacion2.setCapacidad(2);
        habitacion2.setEstadoOcupacion("Ocupado");
        return habitacion2;
    }

    private Habitacion habitacion3() {
        Habitacion habitacion3 = new Habitacion();
        habitacion3.setIdHotel(hotel3);
        habitacion3.setIdProducto(null);
        habitacion3.setNumeroHabitacion(202);
        habitacion3.setPiso(2);
        habitacion3.setTipo("Suite");
        habitacion3.setCapacidad(4);
        habitacion3.setEstadoOcupacion("Libre");
        return habitacion3;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void loadRoles() {
        log.info("Iniciando carga de roles ficticios...");
        if(!rolRepo.existsById(1)) {
            Rol cliente = new Rol();
            cliente.setId(1);
            cliente.setNombreRol("cliente");
            rolRepo.save(cliente);
        }
        if(!rolRepo.existsById(2)) {
            Rol empleado = new Rol();
            empleado.setId(2);
            empleado.setNombreRol("empleado");
            rolRepo.save(empleado);
        }
        if(!rolRepo.existsById(3)) {
            Rol limpieza = new Rol();
            limpieza.setId(3);
            limpieza.setNombreRol("limpieza");
            rolRepo.save(limpieza);
        }
        if(!rolRepo.existsById(4)) {
            Rol admin = new Rol();
            admin.setId(4);
            admin.setNombreRol("admin");
            rolRepo.save(admin);
        }
    }

    public void loadUsuarios() {
        log.info("Iniciando carga de usuarios ficticios...");
        Rol rolCliente = rolRepo.findById(1).orElseThrow();
        Rol rolEmpleado = rolRepo.findById(2).orElseThrow();
        Rol rolLimpieza = rolRepo.findById(3).orElseThrow();
        Rol rolAdmin = rolRepo.findById(4).orElseThrow();
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(crearUsuario(rolCliente, null, "Lucas", "Martínez", "lucas.martinez@mail.com", "pass1234", "Calle A, Madrid", "612345678", LocalDate.of(1985, 7, 12), LocalDate.of(2023, 5, 1), true, "12345678A"));
        usuarios.add(crearUsuario(rolCliente, null, "Sophie", "Dupont", "sophie.dupont@mail.fr", "bonjour2023", "5 Rue Rivoli, Paris", "3312345678", LocalDate.of(1992, 3, 22), LocalDate.of(2024, 1, 15), true, "FR9876543"));
        usuarios.add(crearUsuario(rolCliente, null, "Ahmed", "El-Sayed", "ahmed.sayed@mail.com", "egypt321", "Cairo Road 3, Cairo", "201234567890", LocalDate.of(1990, 12, 1), LocalDate.of(2023, 11, 2), true, "EGP223344"));
        usuarios.add(crearUsuario(rolEmpleado, hotel1, "Marta", "Gómez", "marta.gomez@hotelciudad.com", "empleado1", "Hotel Ciudad, Madrid", "613456789", LocalDate.of(1988, 6, 5), LocalDate.of(2022, 3, 20), true, "78965432Z"));
        usuarios.add(crearUsuario(rolEmpleado, hotel2, "Carlos", "Ruiz", "carlos.ruiz@hotelcampo.com", "empleado2", "Hotel Campo, Sevilla", "611234567", LocalDate.of(1991, 2, 10), LocalDate.of(2022, 6, 15), true, "15975362Y"));
        usuarios.add(crearUsuario(rolLimpieza, hotel1, "Lola", "Fernández", "lola.fernandez@limpieza.com", "limpieza1", "Hotel Ciudad, Piso 1", "622334455", LocalDate.of(1978, 11, 11), LocalDate.of(2021, 1, 10), true, "ESL123456"));
        usuarios.add(crearUsuario(rolLimpieza, hotel2, "Ana", "Torres", "ana.torres@limpieza.com", "limpieza2", "Hotel Campo, Piso 2", "623456789", LocalDate.of(1982, 4, 19), LocalDate.of(2022, 4, 1), true, "ESL654321"));
        usuarios.add(crearUsuario(rolLimpieza, hotel3, "Mateo", "Reyes", "mateo.reyes@limpieza.com", "limpieza3", "Hotel Playa, Piso 3", "624567890", LocalDate.of(1990, 1, 5), LocalDate.of(2023, 9, 20), true, "DNI998877"));
        usuarios.add(crearUsuario(rolAdmin, null, "Admin", "Principal", "admin@hoteles.com", "adminroot", "Oficina Central", "600000001", LocalDate.of(1975, 1, 1), LocalDate.of(2020, 1, 1), true, "ADM0001"));
        usuarios.add(crearUsuario(rolCliente, null, "Emma", "Lopez", "emma.lopez@mail.com", "emmalopez", "Calle Falsa 123, Zaragoza", "611112222", LocalDate.of(1995, 8, 23), LocalDate.of(2024, 4, 3), true, "23456789L"));

        usuarioRepo.saveAll(usuarios);
        log.info("Usuarios cargados: {}", usuarios.size());
    }
    private Usuario crearUsuario(Rol rol, Hotel idHotel, String nombre, String apellidos,
                                 String email, String password, String direccion, String telefono,
                                 LocalDate fechaNacimiento, LocalDate fechaAlta, boolean activo, String dni){
        Usuario usuario = new Usuario();
        usuario.setIdRol(rol);
        usuario.setIdHotel(idHotel);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setFechaAlta(fechaAlta);
        usuario.setActivo(activo);
        usuario.setDni(dni);
        return usuario;
    }


    /*----------------------------------------------------------------------------------------------------------------*/
    public void loadReservas() {
        log.info("Loading reservas ficticias...");
        SaveAllReservas();

        log.info("Reservas ficticias loaded");
    }

    private void SaveAllReservas() {
        List<Reserva> reservas = new ArrayList<>();

        Reserva reserva1 = getReserva1();
        Reserva reserva2 = getReserva2();

        reservas.add(reserva1);
        reservas.add(reserva2);

        reservaRepo.save(reserva1);

    }

    private Reserva getReserva1() {
        Reserva reserva1 = new Reserva();
        reserva1.setIdUsuario(createUsuarioFicticio(999999, "UserPrueba"));
        reserva1.setIdHabitacion(habitacion1());
        reserva1.setFechaEntrada(LocalDate.of(2025, 7, 27));
        reserva1.setFechaSalida(LocalDate.of(2025, 7, 29));
        reserva1.setEstado("Confirmada");
        reserva1.setPax(2);
        reserva1.setFechaReserva(Instant.now());
        reserva1.setComentarios("");
        return reserva1;
    }

    private Reserva getReserva2() {
        Reserva reserva2 = new Reserva();
        reserva2.setIdUsuario(createUsuarioFicticio(999999, "UserPrueba"));
        reserva2.setIdHabitacion(habitacion2());
        reserva2.setFechaEntrada(LocalDate.of(2025, 8, 1));
        reserva2.setFechaSalida(LocalDate.of(2025, 8, 10));
        reserva2.setEstado("Pendiente");
        reserva2.setPax(3);
        reserva2.setFechaReserva(Instant.now());
        reserva2.setComentarios("Alergia almendras");
        return reserva2;
    }

    private Usuario createUsuarioFicticio(Integer id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        return usuario;
    }

}
