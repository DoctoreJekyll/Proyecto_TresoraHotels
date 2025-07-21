package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.FacturaDTO;
import com.atm.buenas_practicas_java.DTOs.ProductoFormularioDTO;
import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.entities.Reserva;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.FacturaRepo;
import com.atm.buenas_practicas_java.repositories.ProductoRepo;
import com.atm.buenas_practicas_java.repositories.ReservaRepo;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FacturaService extends AbstractTemplateServicesEntities <Factura, Integer,FacturaRepo> {

    private static final Logger logger = LoggerFactory.getLogger(FacturaService.class);

    private final ReservaRepo reservaRepo;
    private final ProductoRepo productoRepo;
    private final UsuarioRepo usuarioRepo;
    private final TemplateEngine templateEngine;


    public FacturaService(FacturaRepo repo, ReservaRepo reservaRepo, ProductoRepo productoRepo, UsuarioRepo usuarioRepo, TemplateEngine templateEngine) {
        super(repo);
        this.reservaRepo = reservaRepo;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
        this.templateEngine = templateEngine;
    }

    public Optional<Factura> findByIdWithRelations(Integer id) {
        return getRepo().findByIdWithAllRelations(id);
    }

    /**
     * Genera un FacturaDTO a partir de una reserva, validando su estado, fecha y permisos de usuario.
     *
     * @param idReserva El ID de la reserva.
     * @param idUsuarioAutenticado El ID del usuario que está intentando descargar la factura (para validación de seguridad).
     * @return Un FacturaDTO con los datos de la factura.
     * @throws ResponseStatusException Si la reserva no existe, no está pagada, la fecha de salida no ha pasado, o el usuario no tiene permiso.
     */
    @Transactional(readOnly = true)
    public FacturaDTO generarFacturaDTO(Integer idReserva, Integer idUsuarioAutenticado) {
        logger.info("Intentando generar FacturaDTO para reserva ID: {} por usuario ID: {}", idReserva, idUsuarioAutenticado);

        // 1. Obtener la reserva con sus relaciones para tener todos los datos.
        Reserva reserva = reservaRepo.findByIdWithAllRelations(idReserva)
                .orElseThrow(() -> {
                    logger.warn("Reserva no encontrada con ID: {}", idReserva);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada con ID: " + idReserva);
                });

        // 2. Validación de seguridad: Asegura que el usuario autenticado es el propietario de la reserva.
        if (idUsuarioAutenticado == null || !reserva.getIdUsuario().getId().equals(idUsuarioAutenticado)) {
            logger.warn("Acceso no autorizado a factura de reserva {}. Usuario autenticado: {}, Propietario reserva: {}",
                    idReserva, idUsuarioAutenticado, reserva.getIdUsuario().getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para descargar esta factura.");
        }

        // 3. Validación de estado y fecha
        if (reserva.getEstado() != Reserva.ESTADO_RESERVA.PAGADA) {
            logger.warn("Factura para reserva {} no disponible: Estado no es PAGADA (actual: {})", idReserva, reserva.getEstado());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La factura no está disponible: La reserva no ha sido pagada.");
        }
        // Usamos isBeforeOrEqualTo para ser consistentes con la validación de descarga en HTML que permitiría el mismo día de salida
        if (reserva.getFechaSalida().isAfter(LocalDate.now())) { // Si la fecha de salida es DESPUÉS de hoy, no se permite
            logger.warn("Factura para reserva {} no disponible: Fecha de salida ({}) aún no ha pasado (hoy: {})",
                    idReserva, reserva.getFechaSalida(), LocalDate.now());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La factura no está disponible: La fecha de salida aún no ha pasado.");
        }

        // 4. Mapear datos a FacturaDTO
        FacturaDTO dto = new FacturaDTO();
        dto.setId(reserva.getId());
        dto.setFechaEmision(LocalDate.now()); // Fecha actual como fecha de emisión

        // Datos del usuario
        Usuario usuario = reserva.getIdUsuario();
        if (usuario != null) {
            dto.setNombre(usuario.getNombre());
            dto.setEmail(usuario.getEmail());
        }

        // Datos de la habitación y hotel
        if (reserva.getIdHabitacion() != null) {
            dto.setNumHabitacion(reserva.getIdHabitacion().getNumeroHabitacion());
            if (reserva.getIdHabitacion().getHotel() != null) {
                dto.setHotel(reserva.getIdHabitacion().getHotel().getId()); // <-- CAMBIO AQUI: Establece el nombre del hotel
            }
            if (reserva.getIdHabitacion().getProducto() != null) {
                dto.setPrecioBaseHabitacion(reserva.getIdHabitacion().getProducto().getPrecioBase());
            }
        }
        dto.setFechaEntrada(reserva.getFechaEntrada());
        dto.setFechaSalida(reserva.getFechaSalida());

        // <-- CAMBIO AQUI: Establece el nombre del método de pago
        if (reserva.getMetodoPagoSeleccionado() != null) {
            dto.setMetodoPago(reserva.getMetodoPagoSeleccionado().getNombre());
        }


        // Productos adicionales
        if (reserva.getProductosUsuarios() != null && !reserva.getProductosUsuarios().isEmpty()) {
            List<ProductoFormularioDTO> productosDTOs = reserva.getProductosUsuarios().stream()
                    .map(pu -> {
                        ProductoFormularioDTO pDto = new ProductoFormularioDTO();
                        pDto.setIdProducto(pu.getIdProducto().getId());
                        pDto.setNombreProducto(pu.getIdProducto().getNombre()); // <-- CAMBIO AQUI: Establece el nombre del producto
                        pDto.setCantidad(pu.getCantidad());
                        pDto.setPrecioBase(pu.getIdProducto().getPrecioBase());
                        pDto.setFecha(pu.getFecha());
                        // Si aplicas descuento y lo tienes en la entidad ProductoUsuario, puedes añadirlo aquí
                        // pDto.setDescuento(pu.getDescuento());
                        return pDto;
                    })
                    .collect(Collectors.toList());
            dto.setProductos(productosDTOs);
        }

        // Precio total (asumiendo que reserva.getTotalReserva() ya es el final)
        dto.setPrecioTotal(reserva.getTotalReserva());

        logger.info("FacturaDTO generado con éxito para reserva ID: {}", idReserva);
        logger.debug("Contenido de FacturaDTO: {}", dto);
        return dto;
    }

    /**
     * Genera un PDF a partir de un FacturaDTO utilizando una plantilla Thymeleaf.
     *
     * @param facturaDTO El DTO con los datos de la factura.
     * @return Un array de bytes que representa el contenido del PDF.
     * @throws Exception Si ocurre un error durante la generación del PDF.
     */
    public byte[] generarFacturaPDF(FacturaDTO facturaDTO) throws Exception {
        logger.info("Iniciando generación de PDF para FacturaDTO ID: {}", facturaDTO.getId());

        // 1. Crear el contexto de Thymeleaf y añadir el DTO
        Context context = new Context();
        context.setVariable("factura", facturaDTO);

        // 2. Procesar la plantilla HTML
        String htmlContent = templateEngine.process("factura_pdf_template", context);
        logger.debug("Contenido HTML renderizado para PDF:\n{}", htmlContent);

        // 3. Convertir HTML a PDF usando Flying Saucer
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] pdfBytes = outputStream.toByteArray();
        logger.info("PDF generado correctamente. Tamaño: {} bytes.", pdfBytes.length);
        return pdfBytes;
    }
}