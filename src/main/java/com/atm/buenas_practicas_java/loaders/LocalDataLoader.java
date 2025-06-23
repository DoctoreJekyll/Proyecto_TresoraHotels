package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class LocalDataLoader {
    private final HotelesRepo  hotelesRepo;
    private final HabitacionRepo habitacionRepo;
    private final ProductoRepo  productoRepo;
    private final CategoriaProductoRepo categoriaProductoRepo;

    /**
     * Constructor de la clase {@code LocalDataLoader}.
     *
     * Inicializa un objeto {@code LocalDataLoader} configurado con los repositorios de las entidades,
     * proporcionando la capacidad de interactuar con estas entidades en la base de datos.
     */
    public LocalDataLoader(HotelesRepo  hotelesRepo,  HabitacionRepo habitacionRepo, ProductoRepo  productoRepo, CategoriaProductoRepo categoriaProductoRepo) {
        this.hotelesRepo = hotelesRepo;
        this.habitacionRepo = habitacionRepo;
        this.productoRepo = productoRepo;
        this.categoriaProductoRepo = categoriaProductoRepo;
    }

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
}
