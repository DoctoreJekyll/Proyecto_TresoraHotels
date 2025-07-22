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
 * Esta clase está anotada con:
 * - {@code @Configuration}: Define esta clase como fuente de beans y configuración.
 * - {@code @Log4j2}: Habilita el uso de la biblioteca Log4j2 para registro de mensajes en los logs.
 * - {@code @Profile("default")}: Asegura que esta clase solo se cargue en el perfil "default".
 *
 */
@Configuration
@Log4j2
@Profile("desarrollo")
@RequiredArgsConstructor
public class DesarrolloDataLoader {
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
    private final MiembroEquipoRepo miembroEquipoRepo;

    /** Aquí inyectamos el passwordEncoder para cifrar las contraseñas */
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadDataDesarrollo() {
        System.out.println("Cargando datos desarrollo");
        loadHoteles();
        loadRoles();
        loadUsuarios();
        loadReservas();
        loadLimpiezaHabitaciones();
        loadContactos();
        loadMetodoPago();
        loadFacturas();
        loadMiembrosEquipo();
        System.out.println("Datos cargados desde desarrollo");
        log.info("Datos cargados??");
    }
    public void loadMiembrosEquipo() {
        // Cargar datos del equipo
        MiembroEquipo Alba = new MiembroEquipo(
                "Alba Gutierrez García",
                "images/Alba.jpg",
                "La Arquitecta del Caos",
                Arrays.asList(
                        new RedSocial("LinkedIn", "https://www.linkedin.com/in/alba-guti%C3%A9rrez-garc%C3%ADa/")
                )
        );

        // Asociar las redes sociales con el miembro
        Alba.getRedesSociales().forEach(redSocial -> redSocial.setMiembro(Alba));

        MiembroEquipo Alvaro = new MiembroEquipo(
                "Álvaro Fernández Sevilla",
                "images/Alvaro.jpg",
                "El Ejecutador Silencioso",
                Arrays.asList(
                        new RedSocial("LinkedIn", "https://www.linkedin.com/in/alvaro-fernandez-sevilla/")
                )
        );
        // Asociar las redes sociales con el miembro
        Alvaro.getRedesSociales().forEach(redSocial -> redSocial.setMiembro(Alvaro));

        MiembroEquipo Jose= new MiembroEquipo(
                "Jose Antonio Rodríguez Martin",
                "images/Jose.jpg",
                "Cafetero entre códigos",
                Arrays.asList(
                        new RedSocial("LinkedIn", "https://www.linkedin.com/in/jose-rodriguez-martin/?originalSubdomain=es")
                )
        );
        // Asociar las redes sociales con el miembro
        Jose.getRedesSociales().forEach(redSocial -> redSocial.setMiembro(Jose));


        MiembroEquipo Lucia= new MiembroEquipo(
                "Lucia Beltran Infante",
                "images/Lucia.jpg",
                "",
                Arrays.asList(
                        new RedSocial("LinkedIn", "https://www.linkedin.com/in/luc%C3%ADa-beltr%C3%A1n-infante/")
                )
        );
        // Asociar las redes sociales con el miembro
        Lucia.getRedesSociales().forEach(redSocial -> redSocial.setMiembro(Lucia));

        MiembroEquipo Natalia= new MiembroEquipo(
                "Natalia García Rodríguez",
                "images/Natalia.jpg",
                "",
                Arrays.asList(
                        new RedSocial("LinkedIn", "https://www.linkedin.com/in/natalia-garcia-rodriguez/")
                )
        );

        // Asociar las redes sociales con el miembro
        Natalia.getRedesSociales().forEach(redSocial -> redSocial.setMiembro(Natalia));

        miembroEquipoRepo.saveAll(Arrays.asList(Alba, Alvaro, Jose, Lucia, Natalia));
        System.out.println("Datos del equipo cargados en la base de datos.");
    }

    public void loadHoteles() {
        saveAllHoteles();
        saveAllCategorias();
        saveAllProductos();
        saveAllHabitaciones();
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
        hotel3.setNombre("Playa");
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
        hotel2.setNombre("Montaña");
        hotel2.setDescripcion("Lujo entre montañas y aire puro");
        hotel2.setCiudad("Granada");
        hotel2.setDireccion("Camino de la Sierra, s/n, 18010, Granada");
        hotel2.setDireccionURL("//umap.openstreetmap.fr/es/map/mapa-sin-titulo_1254618?scaleControl=false&miniMap=true&scrollWheelZoom=true&zoomControl=true&editMode=disabled&moreControl=true&searchControl=null&tilelayersControl=null&embedControl=null&datalayersControl=true&onLoadPanel=none&captionBar=false&captionMenus=false&datalayers=e67e5d33-c34d-4abb-95d0-4a009c4b1ba9&homeControl=false#14/37.0983/-3.3994");
        hotel2.setImageURL("https://picsum.photos/600/400?random=8");
        hotel2.setTelefono("+34 622 333 444");
        hotel2.setEmail("reservas@monteverdepalace.com");
        return hotel2;
    }
    private Hotel getHotel() {
        Hotel hotel1 = new Hotel();
        hotel1.setNombre("Ciudad");
        hotel1.setDescripcion("Un paraíso costero para que te lo goces");
        hotel1.setCiudad("Málaga");
        hotel1.setDireccion("Playa de Pedregalejo, Málaga-Este, 29017, Málaga");
        hotel1.setDireccionURL("//umap.openstreetmap.fr/es/map/mapa-sin-titulo_1254618?scaleControl=false&miniMap=true&scrollWheelZoom=true&zoomControl=true&editMode=disabled&moreControl=true&searchControl=null&tilelayersControl=null&embedControl=null&datalayersControl=true&onLoadPanel=none&captionBar=false&captionMenus=true&datalayers=e67e5d33-c34d-4abb-95d0-4a009c4b1ba9");
        hotel1.setImageURL("https://picsum.photos/600/400?random=5");
        hotel1.setTelefono("+34 633 111 222");
        hotel1.setEmail("info@tresorabeach.com");
        return hotel1;
    }

    // Campos declarados fuera
    Habitacion habitacion;
    Habitacion habitacion2;
    Habitacion habitacion3;
    Habitacion habitacion4;
    Habitacion habitacion5;
    Habitacion habitacion6;
    Habitacion habitacion7;
    Habitacion habitacion8;
    Habitacion habitacion9;
    Habitacion habitacion10;
    Habitacion habitacion11;
    Habitacion habitacion12;

    private void saveAllHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();

        habitacion = habitacion1();
        habitacion2 = habitacion2();
        habitacion3 = habitacion3();
        habitacion4 = habitacion4();
        habitacion5 = habitacion5();
        habitacion6 = habitacion6();
        habitacion7 = habitacion7();
        habitacion8 = habitacion8();
        habitacion9 = habitacion9();
        habitacion10 = habitacion10();
        habitacion11 = habitacion11();
        habitacion12 = habitacion12();

        habitaciones.add(habitacion);
        habitaciones.add(habitacion2);
        habitaciones.add(habitacion3);
        habitaciones.add(habitacion4);
        habitaciones.add(habitacion5);
        habitaciones.add(habitacion6);
        habitaciones.add(habitacion7);
        habitaciones.add(habitacion8);
        habitaciones.add(habitacion9);
        habitaciones.add(habitacion10);
        habitaciones.add(habitacion11);
        habitaciones.add(habitacion12);

        habitacionRepo.saveAll(habitaciones);
    }

    // HOTEL 1
    private Habitacion habitacion1() {
        habitacion = new Habitacion();
        habitacion.setHotel(hotel1);
        habitacion.setProducto(producto);
        habitacion.setNumeroHabitacion(1);
        habitacion.setPiso(0);
        habitacion.setTipo("Individual");
        habitacion.setCapacidad(1);
        habitacion.setOcupada(false);
        habitacion.setImagenUrl(defaultImage());
        return habitacion;
    }

    private Habitacion habitacion5() {
        habitacion5 = new Habitacion();
        habitacion5.setHotel(hotel1);
        habitacion5.setProducto(producto2);
        habitacion5.setNumeroHabitacion(2);
        habitacion5.setPiso(0);
        habitacion5.setTipo("Doble");
        habitacion5.setCapacidad(2);
        habitacion5.setOcupada(false);
        habitacion5.setImagenUrl(defaultImage());
        return habitacion5;
    }

    private Habitacion habitacion6() {
        habitacion6 = new Habitacion();
        habitacion6.setHotel(hotel1);
        habitacion6.setProducto(producto3);
        habitacion6.setNumeroHabitacion(3);
        habitacion6.setPiso(1);
        habitacion6.setTipo("Triple");
        habitacion6.setCapacidad(3);
        habitacion6.setOcupada(true);
        habitacion6.setImagenUrl(defaultImage());
        return habitacion6;
    }

    private Habitacion habitacion7() {
        habitacion7 = new Habitacion();
        habitacion7.setHotel(hotel1);
        habitacion7.setProducto(producto);
        habitacion7.setNumeroHabitacion(4);
        habitacion7.setPiso(1);
        habitacion7.setTipo("Suite");
        habitacion7.setCapacidad(4);
        habitacion7.setOcupada(false);
        habitacion7.setImagenUrl(defaultImage());
        return habitacion7;
    }

    // HOTEL 2
    private Habitacion habitacion2() {
        habitacion2 = new Habitacion();
        habitacion2.setHotel(hotel2);
        habitacion2.setProducto(producto2);
        habitacion2.setNumeroHabitacion(101);
        habitacion2.setPiso(1);
        habitacion2.setTipo("Doble");
        habitacion2.setCapacidad(2);
        habitacion2.setOcupada(false);
        habitacion2.setImagenUrl(defaultImage());
        return habitacion2;
    }

    private Habitacion habitacion8() {
        habitacion8 = new Habitacion();
        habitacion8.setHotel(hotel2);
        habitacion8.setProducto(producto3);
        habitacion8.setNumeroHabitacion(102);
        habitacion8.setPiso(1);
        habitacion8.setTipo("Triple");
        habitacion8.setCapacidad(3);
        habitacion8.setOcupada(true);
        habitacion8.setImagenUrl(defaultImage());
        return habitacion8;
    }

    private Habitacion habitacion9() {
        habitacion9 = new Habitacion();
        habitacion9.setHotel(hotel2);
        habitacion9.setProducto(producto);
        habitacion9.setNumeroHabitacion(103);
        habitacion9.setPiso(2);
        habitacion9.setTipo("Suite");
        habitacion9.setCapacidad(4);
        habitacion9.setOcupada(false);
        habitacion9.setImagenUrl(defaultImage());
        return habitacion9;
    }

    private Habitacion habitacion10() {
        habitacion10 = new Habitacion();
        habitacion10.setHotel(hotel2);
        habitacion10.setProducto(producto2);
        habitacion10.setNumeroHabitacion(104);
        habitacion10.setPiso(2);
        habitacion10.setTipo("Individual");
        habitacion10.setCapacidad(1);
        habitacion10.setOcupada(false);
        habitacion10.setImagenUrl(defaultImage());
        return habitacion10;
    }

    // HOTEL 3
    private Habitacion habitacion3() {
        habitacion3 = new Habitacion();
        habitacion3.setHotel(hotel3);
        habitacion3.setProducto(producto2);
        habitacion3.setNumeroHabitacion(201);
        habitacion3.setPiso(2);
        habitacion3.setTipo("Suite");
        habitacion3.setCapacidad(4);
        habitacion3.setOcupada(true);
        habitacion3.setImagenUrl(defaultImage());
        return habitacion3;
    }

    private Habitacion habitacion4() {
        habitacion4 = new Habitacion();
        habitacion4.setHotel(hotel3);
        habitacion4.setProducto(producto3);
        habitacion4.setNumeroHabitacion(202);
        habitacion4.setPiso(2);
        habitacion4.setTipo("Suite");
        habitacion4.setCapacidad(4);
        habitacion4.setOcupada(false);
        habitacion4.setImagenUrl(defaultImage());
        return habitacion4;
    }

    private Habitacion habitacion11() {
        habitacion11 = new Habitacion();
        habitacion11.setHotel(hotel3);
        habitacion11.setProducto(producto);
        habitacion11.setNumeroHabitacion(203);
        habitacion11.setPiso(3);
        habitacion11.setTipo("Doble");
        habitacion11.setCapacidad(2);
        habitacion11.setOcupada(false);
        habitacion11.setImagenUrl(defaultImage());
        return habitacion11;
    }

    private Habitacion habitacion12() {
        habitacion12 = new Habitacion();
        habitacion12.setHotel(hotel3);
        habitacion12.setProducto(producto2);
        habitacion12.setNumeroHabitacion(204);
        habitacion12.setPiso(3);
        habitacion12.setTipo("Individual");
        habitacion12.setCapacidad(1);
        habitacion12.setOcupada(false);
        habitacion12.setImagenUrl(defaultImage());
        return habitacion12;
    }

    // Imagen reutilizable
    private String defaultImage() {
        return "https://media.istockphoto.com/id/1334117383/es/foto/representaci%C3%B3n-digital-3d-de-una-suite-de-hotel-de-lujo.jpg?s=2048x2048&w=is&k=20&c=g7SxwCPRtqqK9DANIUyQqmY1MupgIz7fznf5JnZT1vA=";
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
        producto.setIdCategoria(categoriaProducto);
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
        producto.setIdCategoria(categoriaProducto2);
        producto.setNombre("Servicio de Restaurante");
        producto.setDescripcion("Restaurante bien guapa");
        producto.setPrecioBase(10.0);
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
        categoriaProducto.setNombre("Habitacion");
        categoriaProducto.setDescripcion("Categoria para las distintas habitaciones");
        return categoriaProducto;
    }

    private CategoriaProducto getCategoriaProducto2() {
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setNombre("Servicios");
        categoriaProducto.setDescripcion("Categorias para los distintos servicios");
        return categoriaProducto;
    }

    private CategoriaProducto getCategoriaProducto3() {
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setNombre("Otros");
        categoriaProducto.setDescripcion("Categorias para productos del hotel");
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
        SaveAllReservas();
    }

    private Reserva reserva1;
    private Reserva reserva2;
    private Reserva reserva3;

    private void SaveAllReservas() {
        List<Reserva> reservas = new ArrayList<>();
        reserva1 = getReserva1();
        reserva2 = getReserva2();
        reserva3 = getReserva3();
        reservas.add(reserva1);
        reservas.add(reserva2);
        reservas.add(reserva3);
        reservaRepo.saveAll(reservas);
    }

    private Reserva getReserva1() {
        Reserva reserva1 = new Reserva();
        reserva1.setIdUsuario(usuarioRepo.getReferenceById(2));
        reserva1.setIdHabitacion(habitacion2);
        reserva1.setFechaEntrada(LocalDate.of(2025, 7, 27));
        reserva1.setFechaSalida(LocalDate.of(2025, 7, 29));
        reserva1.setEstado(Reserva.ESTADO_RESERVA.PAGADA);
        reserva1.setPax(2);
        reserva1.setFechaReserva(Instant.now());
        reserva1.setComentarios("");
        reserva1.setTotalReserva(200);
        return reserva1;
    }

    private Reserva getReserva2() {
        Reserva reserva2 = new Reserva();
        reserva2.setIdUsuario(usuarioRepo.getReferenceById(1));
        reserva2.setIdHabitacion(habitacion3);
        reserva2.setFechaEntrada(LocalDate.of(2025, 8, 1));
        reserva2.setFechaSalida(LocalDate.of(2025, 8, 10));
        reserva2.setEstado(Reserva.ESTADO_RESERVA.PAGADA);
        reserva2.setPax(3);
        reserva2.setFechaReserva(Instant.now());
        reserva2.setComentarios("Alergia almendras");
        reserva2.setTotalReserva(100);
        return reserva2;
    }

    private Reserva getReserva3() {
        Reserva reserva3 = new Reserva();
        reserva3.setIdUsuario(usuarioRepo.getReferenceById(9));
        reserva3.setIdHabitacion(habitacion3);
        reserva3.setFechaEntrada(LocalDate.of(2025, 6, 1));
        reserva3.setFechaSalida(LocalDate.of(2025, 6, 10));
        reserva3.setEstado(Reserva.ESTADO_RESERVA.PAGADA);
        reserva3.setPax(3);
        reserva3.setFechaReserva(Instant.now());
        reserva3.setComentarios("Alergia almendras");
        reserva3.setTotalReserva(100);
        return reserva3;
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    public void loadLimpiezaHabitaciones() {
        SaveAllLimpiezaHabitaciones();
    }

    private LimpiezaHabitaciones limpiezaHabitacion1;
    private LimpiezaHabitaciones limpiezaHabitacion2;

    private void SaveAllLimpiezaHabitaciones(){
        List<LimpiezaHabitaciones> limpiezas = new ArrayList<>();

        limpiezaHabitacion1 = getLimpiezaHabitacion1();
        limpiezaHabitacion2 = getLimpiezaHabitacion2();
        limpiezas.add(limpiezaHabitacion1);
        limpiezas.add(limpiezaHabitacion2);

        limpiezaHabitacionesRepo.saveAll(limpiezas);

    }

    private LimpiezaHabitaciones getLimpiezaHabitacion1() {
        com.atm.buenas_practicas_java.entities.LimpiezaHabitaciones limpiezaHabitacion1 = new LimpiezaHabitaciones();

        limpiezaHabitacion1.setIdUsuario(usuarioRepo.getReferenceById(2));
        limpiezaHabitacion1.setIdHabitacion(habitacion2);
        limpiezaHabitacion1.setFechaLimpieza(LocalDate.of(2025, 7, 29));
        limpiezaHabitacion1.setHoraLimpieza(LocalTime.of(12, 23));
        limpiezaHabitacion1.setFoto1("https://media.istockphoto.com/id/1086675290/es/foto/retrete-sucio-poco-higi%C3%A9nicas-con-manchas-de-cal-en-el-ba%C3%B1o-p%C3%BAblico-cerca.jpg?s=2048x2048&w=is&k=20&c=fWHGeQZ-YdQNgPMLYZ03QcvRAzpLXYBFIBlUkCComtk=");
        limpiezaHabitacion1.setFoto2("");
        return limpiezaHabitacion1;
    }
    private LimpiezaHabitaciones getLimpiezaHabitacion2() {
        LimpiezaHabitaciones limpiezaHabitacion2 = new LimpiezaHabitaciones();

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
        saveAllContactos();

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

        facturaRepo.save(factura1);
    }

    private MetodoPago metodoPago1;
    private MetodoPago metodoPago2;

    public MetodoPago getMetodoPago1(){
        MetodoPago metodoPago1 = new MetodoPago();
        metodoPago1.setNombre("Bizum");
        metodoPago1.setDescripcion("El pago se ha realizado al bizum del hotel");
        return metodoPago1;
    }

    public MetodoPago getMetodoPago2(){
        MetodoPago metodoPago2 = new MetodoPago();
        metodoPago2.setNombre("Tarjeta");
        metodoPago2.setDescripcion("El pago se ha realizado a la cuenta del hotel");
        return metodoPago2;
    }

    public void loadMetodoPago(){
        metodoPago1 = getMetodoPago1();
        metodoPago2 = getMetodoPago2();

        List<MetodoPago> metodoPagos = new ArrayList<>();
        metodoPagos.add(metodoPago1);
        metodoPagos.add(metodoPago2);

        metodoPagoRepo.saveAll(metodoPagos);
    }
}



