package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.repositories.ReservaRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
@RequiredArgsConstructor
public class LocalDataLoader {
    private final HotelesRepo  hotelesRepo;
    private final HabitacionRepo habitacionRepo;
    private final ProductoRepo  productoRepo;
    private final CategoriaProductoRepo categoriaProductoRepo;
    private final UsuarioRepo usuarioRepo;
    private final RolRepo rolRepo;
    private final ReservaRepo reservaRepo;
    private final LimpiezaHabitacionesRepo  limpiezaHabitacionesRepo;
    private final ContactoRepo contactoRepo;
    private final FacturaRepo facturaRepo;
    private final MetodoPagoRepo metodoPagoRepo;

    /** Aquí inyectamos el passwordEncoder para cifrar las contraseñas */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor de la clase {@code LocalDataLoader}.
     *
     * Inicializa un objeto {@code LocalDataLoader} configurado con los repositorios de las entidades,
     * proporcionando la capacidad de interactuar con estas entidades en la base de datos.
     */

    /**
     * Método anotado con {@code @PostConstruct} que inicializa datos de prueba en
     * los repositorios para entornos locales. Este método se ejecuta automáticamente
     * después de la inicialización del bean y antes de que esté disponible para uso,
     * permitiendo cargar datos iniciales necesarios para el perfil local.
     *
     * Funcionalidad:
     * - Crea 10 instancias de la entidad `EntidadPadre` con nombres predefinidos.
     * - Guarda las instancias de `EntidadPadre` en el repositorio correspondiente.
     * - Para cada instancia de `EntidadPadre`, crea una entidad relacionada de tipo
     *   `EntidadHija` con un nombre identificativo, y la asocia a la entidad padre
     *   pertinente.
     * - Guarda las entidades hijas en el repositorio `entidadHijaRepository`.
     * - Registra mensajes informativos en el log sobre el inicio y finalización del proceso.
     *
     * Proceso:
     * 1. Se define un número fijo de entidades padre (10).
     * 2. Se utiliza un array para almacenar las instancias y se inicializa con un nombre
     *    único para cada entidad padre.
     * 3. Todas las entidades padre se guardan de forma simultánea utilizando
     *    {@code repository.saveAll}.
     * 4. Para cada entidad padre, se crea una instancia de la entidad hija, se establece
     *    la relación con el padre y se guarda en el repositorio correspondiente.
     * 5. Se registran logs informativos sobre el estado del proceso.
     *
     * Dependencias principales:
     * - `repository`: {@code EntidadPadreRepository}, usado para almacenar las entidades padre.
     * - `entidadHijaRepository`: {@code EntidadHijaRepository}, usado para guardar las entidades hijas.
     *
     * Importante:
     * - Este método está diseñado específicamente para ser utilizado en entornos con
     *   el perfil local activo.
     * - No debe usarse en entornos de producción, ya que sobrescribirá datos existentes.
     *
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
        loadLimpiezaHabitaciones();
        loadContactos();
        loadMetodoPago();
        loadFacturas();
    }

    public void loadHoteles() {
        log.info("Iniciando carga de hoteles ficticios...");

        saveAllHoteles();
        saveAllCategorias();
        saveAllProductos();
        saveAllHabitaciones();

        log.info("Carga completada");
    }

    private Hotel hotel1;
    private Hotel hotel2;
    private Hotel hotel3;

    private void saveAllHoteles()
    {
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

    Habitacion habitacion;
    Habitacion habitacion2;
    Habitacion habitacion3;

    private void saveAllHabitaciones()
    {
        List<Habitacion> habitaciones = new ArrayList<>();

        habitacion = habitacion1();
        habitacion2 = habitacion2();
        habitacion3 = habitacion3();
        habitaciones.add(habitacion);
        habitaciones.add(habitacion2);
        habitaciones.add(habitacion3);

        habitacionRepo.saveAll(habitaciones);
    }

    private Habitacion habitacion1()
    {
        Habitacion habitacion1 = new Habitacion();
        habitacion1.setIdHotel(hotel1);
        habitacion1.setIdProducto(producto);
        habitacion1.setNumeroHabitacion(0);
        habitacion1.setPiso(0);
        habitacion1.setTipo("Individual");
        habitacion1.setCapacidad(1);
        habitacion1.setEstadoOcupacion("Libre");
        habitacion1.setImagenUrl("no image");
        return habitacion1;
    }
    private Habitacion habitacion2() {
        Habitacion habitacion2 = new Habitacion();
        habitacion2.setIdHotel(hotel2);
        habitacion2.setIdProducto(producto2);
        habitacion2.setNumeroHabitacion(101);
        habitacion2.setPiso(1);
        habitacion2.setTipo("Doble");
        habitacion2.setCapacidad(2);
        habitacion2.setEstadoOcupacion("Ocupado");
        habitacion2.setImagenUrl("no image");
        return habitacion2;
    }

    private Habitacion habitacion3() {
        Habitacion habitacion3 = new Habitacion();
        habitacion3.setIdHotel(hotel3);
        habitacion3.setIdProducto(producto3);
        habitacion3.setNumeroHabitacion(202);
        habitacion3.setPiso(2);
        habitacion3.setTipo("Suite");
        habitacion3.setCapacidad(4);
        habitacion3.setEstadoOcupacion("Libre");
        habitacion3.setImagenUrl("no image");
        return habitacion3;
    }

    private Producto producto;
    private Producto producto2;
    private Producto producto3;

    private Producto getProducto()
    {
        Producto producto = new Producto();
        producto.setIdHotel(hotel1.getId());
        producto.setIdCategoria(categoriaProducto);
        producto.setNombre("Habitacion simple");
        producto.setDescripcion("Habitacion simple bien guapa");
        producto.setPrecioBase(11.0);
        producto.setActivo(true);
        producto.setFechaDesde(LocalDate.EPOCH);
        producto.setFechaHasta(LocalDate.EPOCH);
        return producto;
    }

    private Producto getProducto2() {
        Producto producto = new Producto();
        producto.setIdHotel(hotel2.getId());
        producto.setIdCategoria(categoriaProducto2);
        producto.setNombre("Suite de lujo");
        producto.setDescripcion("Habitación con cama king size, jacuzzi y vistas al mar");
        producto.setPrecioBase(323.0);
        producto.setActivo(true);
        producto.setFechaDesde(LocalDate.of(2025, 1, 1));
        producto.setFechaHasta(LocalDate.of(2025, 12, 31));
        return producto;
    }

    private Producto getProducto3() {
        Producto producto = new Producto();
        producto.setIdHotel(hotel3.getId());
        producto.setIdCategoria(categoriaProducto3);
        producto.setNombre("Habitación doble");
        producto.setDescripcion("Habitación con dos camas individuales y baño privado");
        producto.setPrecioBase(30.0);
        producto.setActivo(true);
        producto.setFechaDesde(LocalDate.of(2025, 6, 1));
        producto.setFechaHasta(LocalDate.of(2025, 12, 31));
        return producto;
    }

    private void saveAllProductos()
    {
        List<Producto> productos = new ArrayList<>();
        producto = getProducto();
        producto2 = getProducto2();
        producto3 = getProducto3();
        productos.add(producto);
        productos.add(producto2);
        productos.add(producto3);
        productoRepo.saveAll(productos);
    }

    private CategoriaProducto categoriaProducto;
    private CategoriaProducto categoriaProducto2;
    private CategoriaProducto categoriaProducto3;

    private CategoriaProducto getCategoriaProducto()
    {
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setNombre("Habitacion categoria 1");
        categoriaProducto.setDescripcion("Habitacion categoria 1 descripcion");
        return categoriaProducto;
    }

    private CategoriaProducto getCategoriaProducto2() {
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setNombre("Habitación categoría 2");
        categoriaProducto.setDescripcion("Habitación categoría 2 con más comodidades");
        return categoriaProducto;
    }

    private CategoriaProducto getCategoriaProducto3() {
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setNombre("Habitación categoría lujo");
        categoriaProducto.setDescripcion("Habitación de lujo con servicios exclusivos");
        return categoriaProducto;
    }

    private void saveAllCategorias()
    {
        List<CategoriaProducto> categorias = new ArrayList<>();
        categoriaProducto = getCategoriaProducto();
        categoriaProducto2 = getCategoriaProducto2();
        categoriaProducto3 = getCategoriaProducto3();
        categorias.add(categoriaProducto);
        categorias.add(categoriaProducto2);
        categorias.add(categoriaProducto3);
        categoriaProductoRepo.saveAll(categorias);
    }

    /*---------------------------------------------------------------------------------------------------------------*/

    private Rol rolCliente;
    private Rol rolEmpleado;
    private Rol rolLimpieza;
    private Rol rolAdmin;

    public void loadRoles() {
        rolCliente = new Rol();
        rolCliente.setNombreRol("CLIENTE");

        rolEmpleado = new Rol();
        rolEmpleado.setNombreRol("EMPLEADO");

        rolLimpieza = new Rol();
        rolLimpieza.setNombreRol("LIMPIEZA");

        rolAdmin = new Rol();
        rolAdmin.setNombreRol("ADMIN");

        rolRepo.saveAll(Arrays.asList(rolCliente, rolEmpleado, rolLimpieza, rolAdmin));
    }

    public void loadUsuarios() {
        log.info("Iniciando carga de usuarios ficticios...");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(crearUsuario(rolCliente, null, "Lucas", "Martínez", "lucas.martinez@mail.com", passwordEncoder.encode("pass1234"), "Calle A, Madrid", "612345678", LocalDate.of(1985, 7, 12), LocalDate.of(2023, 5, 1), true, "12345678A"));
        usuarios.add(crearUsuario(rolCliente, null, "Sophie", "Dupont", "sophie.dupont@mail.fr", passwordEncoder.encode("bonjour2023"), "5 Rue Rivoli, Paris", "3312345678", LocalDate.of(1992, 3, 22), LocalDate.of(2024, 1, 15), true, "FR9876543"));
        usuarios.add(crearUsuario(rolCliente, null, "Ahmed", "El-Sayed", "ahmed.sayed@mail.com", passwordEncoder.encode("egypt321"), "Cairo Road 3, Cairo", "201234567890", LocalDate.of(1990, 12, 1), LocalDate.of(2023, 11, 2), true, "EGP223344"));
        usuarios.add(crearUsuario(rolEmpleado, hotel1, "Marta", "Gómez", "marta.gomez@hotelciudad.com", passwordEncoder.encode("empleado1"), "Hotel Ciudad, Madrid", "613456789", LocalDate.of(1988, 6, 5), LocalDate.of(2022, 3, 20), true, "78965432Z"));
        usuarios.add(crearUsuario(rolEmpleado, hotel2, "Carlos", "Ruiz", "carlos.ruiz@hotelcampo.com", passwordEncoder.encode("empleado2"), "Hotel Campo, Sevilla", "611234567", LocalDate.of(1991, 2, 10), LocalDate.of(2022, 6, 15), true, "15975362Y"));
        usuarios.add(crearUsuario(rolLimpieza, hotel1, "Lola", "Fernández", "lola.fernandez@limpieza.com", passwordEncoder.encode("limpieza1"), "Hotel Ciudad, Piso 1", "622334455", LocalDate.of(1978, 11, 11), LocalDate.of(2021, 1, 10), true, "ESL123456"));
        usuarios.add(crearUsuario(rolLimpieza, hotel2, "Ana", "Torres", "ana.torres@limpieza.com", passwordEncoder.encode("limpieza2"), "Hotel Campo, Piso 2", "623456789", LocalDate.of(1982, 4, 19), LocalDate.of(2022, 4, 1), true, "ESL654321"));
        usuarios.add(crearUsuario(rolLimpieza, hotel3, "Mateo", "Reyes", "mateo.reyes@limpieza.com", passwordEncoder.encode("limpieza3"), "Hotel Playa, Piso 3", "624567890", LocalDate.of(1990, 1, 5), LocalDate.of(2023, 9, 20), true, "DNI998877"));
        usuarios.add(crearUsuario(rolAdmin, null, "Admin", "Principal", "admin@hoteles.com", passwordEncoder.encode("adminroot"), "Oficina Central", "600000001", LocalDate.of(1975, 1, 1), LocalDate.of(2020, 1, 1), true, "ADM0001"));
        usuarios.add(crearUsuario(rolCliente, null, "Emma", "Lopez", "emma.lopez@mail.com", passwordEncoder.encode("emmalopez"), "Calle Falsa 123, Zaragoza", "611112222", LocalDate.of(1995, 8, 23), LocalDate.of(2024, 4, 3), true, "23456789L"));

        usuarioRepo.saveAll(usuarios);
        log.info("Usuarios cargados: {}", usuarios.size());
    }


    private Usuario crearUsuario(Rol rol, Hotel idHotel, String nombre, String apellidos,
                                 String email, String password, String direccion, String telefono,
                                 LocalDate fechaNacimiento, LocalDate fechaAlta, boolean activo, String dni) {
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

/*-------------------------------------------------------------------------------------------------------------------*/
public void loadReservas() {
    log.info("Loading reservas ficticias...");
    SaveAllReservas();

    log.info("Reservas ficticias loaded");
}

    private Reserva reserva1;
    private Reserva reserva2;

    private void SaveAllReservas() {
        List<Reserva> reservas = new ArrayList<>();
        reserva1 = getReserva1();
        reserva2 = getReserva2();
        reservas.add(reserva1);
        reservas.add(reserva2);
        reservaRepo.saveAll(reservas);
    }

    private Reserva getReserva1() {
        Reserva reserva1 = new Reserva();
        reserva1.setIdUsuario(usuarioRepo.getReferenceById(2));
        reserva1.setIdHabitacion(habitacion2);
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
        reserva2.setIdUsuario(usuarioRepo.getReferenceById(5));
        reserva2.setIdHabitacion(habitacion3);
        reserva2.setFechaEntrada(LocalDate.of(2025, 8, 1));
        reserva2.setFechaSalida(LocalDate.of(2025, 8, 10));
        reserva2.setEstado("Pendiente");
        reserva2.setPax(3);
        reserva2.setFechaReserva(Instant.now());
        reserva2.setComentarios("Alergia almendras");
        return reserva2;
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    public void loadLimpiezaHabitaciones() {
        log.info("Cargando datos de limpieza de habitaciones...");
        SaveAllLimpiezaHabitaciones();

        log.info("Datos de limpieza cargados");
    }

    private LimpiezaHabitacion limpiezaHabitacion1;
    private LimpiezaHabitacion limpiezaHabitacion2;

    private void SaveAllLimpiezaHabitaciones(){
        List<LimpiezaHabitacion> limpiezas = new ArrayList<>();

        limpiezaHabitacion1 = getLimpiezaHabitacion1();
        limpiezaHabitacion2 = getLimpiezaHabitacion2();
        limpiezas.add(limpiezaHabitacion1);
        limpiezas.add(limpiezaHabitacion2);

        limpiezaHabitacionesRepo.saveAll(limpiezas);

    }

    private LimpiezaHabitacion getLimpiezaHabitacion1() {
        LimpiezaHabitacion limpiezaHabitacion1 = new LimpiezaHabitacion();

        limpiezaHabitacion1.setIdUsuario(usuarioRepo.getReferenceById(2));
        limpiezaHabitacion1.setIdHabitacion(habitacion2);
        limpiezaHabitacion1.setFechaLimpieza(LocalDate.of(2025, 7, 29));
        limpiezaHabitacion1.setHoraLimpieza(LocalTime.of(12, 23));
        limpiezaHabitacion1.setFoto1("https://media.istockphoto.com/id/1086675290/es/foto/retrete-sucio-poco-higi%C3%A9nicas-con-manchas-de-cal-en-el-ba%C3%B1o-p%C3%BAblico-cerca.jpg?s=2048x2048&w=is&k=20&c=fWHGeQZ-YdQNgPMLYZ03QcvRAzpLXYBFIBlUkCComtk=");
        limpiezaHabitacion1.setFoto2("");
        return limpiezaHabitacion1;
    }
    private LimpiezaHabitacion getLimpiezaHabitacion2() {
        LimpiezaHabitacion limpiezaHabitacion2 = new LimpiezaHabitacion();

        limpiezaHabitacion2.setIdUsuario(usuarioRepo.getReferenceById(2));
        limpiezaHabitacion2.setIdHabitacion(habitacion3);
        limpiezaHabitacion2.setFechaLimpieza(LocalDate.of(2025, 8, 1));
        limpiezaHabitacion2.setHoraLimpieza(LocalTime.of(10, 11));
        limpiezaHabitacion2.setFoto1("https://media.istockphoto.com/id/2149016574/es/foto/almohada-sucia-en-mano-de-mujer.jpg?s=2048x2048&w=is&k=20&c=2_jT6XjWtKCA6ETydDOZDEzAo4fYOT8BCb_wLFCEaxs=");
        limpiezaHabitacion2.setFoto2("https://media.istockphoto.com/id/2161307161/es/foto/stains-on-white-shirts.jpg?s=2048x2048&w=is&k=20&c=TleWVC9MOgi5lI3nXkqubTZCYHIFrTzdlfXmxLkxlPU=");
        return limpiezaHabitacion2;
    }
    /*---------------------------------------------------------------------------------------------------------------*/

    private Contacto contacto1;
    private Contacto contacto2;

    public void loadContactos(){
        log.info("Iniciando la carga de contactos ficticios...");
        saveAllContactos();

        log.info("Datos de contactos cargados");

    }

    private void saveAllContactos(){
        List<Contacto> contactos = new ArrayList<>();
        contacto1 = getContacto1();
        contacto2 = getContacto2();
        contactos.add(contacto1);
        contactos.add(contacto2);
        contactoRepo.saveAll(contactos);
    }

    private Contacto getContacto1() {
        contacto1 = new Contacto();
        contacto1.setNombre("Ana Gómez");
        contacto1.setCorreo("ana.gomez@example.com");
        contacto1.setTelefono("123456789");
        contacto1.setIdUsuario(null); // No está asociado a ningún usuario
        contacto1.setDepartamento("Habitaciones");
        contacto1.setMensaje("¿Habría posibilidad de llevar a mi mascota?");
        return contacto1;
    }

    private Contacto getContacto2() {
        contacto2 = new Contacto();
        contacto2.setNombre("Carlos Ruiz");
        contacto2.setCorreo("carlos.ruiz@example.com");
        contacto2.setTelefono("987654321");
        contacto2.setIdUsuario(null);
        contacto2.setDepartamento("Habitaciones");
        contacto2.setMensaje("Me gustaría hablar con el departamento de reservas");
        return contacto2;
    }

    private Factura factura1;

    public Factura getFactura1() {
        Factura factura1 = new Factura();
        factura1.setIdUsuario(usuarioRepo.getReferenceById(1));
        factura1.setIdMetodoPago(metodoPago1);
        factura1.setIdDetalle(7);
        factura1.setIdHotel(hotel1);
        factura1.setFechaEmision(LocalDate.of(2025, 8, 1));
        factura1.setEstado("pendiente");
        factura1.setObservaciones("todo correcto");
        return factura1;
    }

    public void loadFacturas(){
        factura1 = getFactura1();

        log.info("Iniciando la carga de facturas ...");
        facturaRepo.save(factura1);
        log.info("Datos de facturas cargados");
    }

    private MetodoPago metodoPago1;

    public MetodoPago getMetodoPago1(){
       MetodoPago metodoPago1 = new MetodoPago();
        metodoPago1.setNombre("Bizum");
        metodoPago1.setDescripcion("El pago se ha realizado al bizum del hotel");
        return metodoPago1;
    }

    public void loadMetodoPago(){
        metodoPago1=getMetodoPago1();

        log.info("Iniciando la carga de métodos de pago");
        metodoPagoRepo.save(metodoPago1);
        log.info("Datos de métodos de pago cargados :)");
    }
}



