package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.FacturaDTO;
import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.entities.Usuario; // Asegúrate de que esta importación es correcta para tu clase Usuario
import com.atm.buenas_practicas_java.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// Importaciones de Spring Security
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

// Importaciones para Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private static final Logger logger = LoggerFactory.getLogger(FacturaController.class); // Instancia del Logger

    private final FacturaService facturaService;
    private final HotelService hotelService;
    private final UsuarioService usuarioService; // Necesario para obtener el ID del usuario
    private final MetodoPagoService metodoPagoService;
    private final EmailService emailService;

    public FacturaController(FacturaService facturaService, HotelService hotelService, UsuarioService usuarioService, MetodoPagoService metodoPagoService, EmailService emailService) {
        this.facturaService = facturaService;
        this.hotelService = hotelService;
        this.usuarioService = usuarioService;
        this.metodoPagoService = metodoPagoService;
        this.emailService = emailService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioFactura (Model model) {
        Factura factura = new Factura();
        model.addAttribute("factura", factura);

        List<Hotel> hoteles = hotelService.findAll();
        model.addAttribute("hoteles", hoteles);

        List<Usuario> usuarios = usuarioService.findAllEntities();
        model.addAttribute("usuarios", usuarios);

        List<MetodoPago> metodosPagos = metodoPagoService.findAll();
        model.addAttribute("metodosPagos", metodosPagos);

        return "facturaForm";
    }

    @PostMapping("/guardar")
    public String guardarFactura(@ModelAttribute Factura factura) {
        facturaService.save(factura);

        String msg = "";
        // No hay descuentos, solo el estado de la factura
        if (factura.getEstado().contains("pagada")) {
            msg = "Factura pagada con éxito";
        } else {
            msg = "Factura pendiente";
        }

        return "redirect:/lista/facturas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioFacturasEditar (@PathVariable Integer id, Model model) {
        Factura factura = facturaService.findByIdWithRelations(id)
                .orElseThrow (()-> new RuntimeException("Factura no encontrada"));
        model.addAttribute("factura", factura);

        List<Hotel> hoteles = hotelService.findAll();
        model.addAttribute("hoteles", hoteles);

        List<Usuario> usuarios = usuarioService.findAllEntities();
        model.addAttribute("usuarios", usuarios);

        List<MetodoPago> metodoPagos = metodoPagoService.findAll();
        model.addAttribute("metodosPagos", metodoPagos);

        return "facturaForm";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarFactura(@PathVariable Integer id) {
        facturaService.deleteById(id);
        return "redirect:/lista/facturas";
    }

    @GetMapping("/descargar-factura/{idReserva}")
    public ResponseEntity<byte[]> descargarFactura(@PathVariable Integer idReserva) {
        Integer idUsuarioAutenticado = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // Asume que tu UsuarioService tiene un método para encontrar un usuario por su 'username' (ej. email)
                // y que ese usuario tiene un método getId().
                idUsuarioAutenticado = usuarioService.findByEmail(username)
                        .map(Usuario::getId)
                        .orElse(null);
            } else if (principal instanceof Usuario) { // Si el objeto principal ya es tu entidad Usuario directamente
                idUsuarioAutenticado = ((Usuario) principal).getId();
            }
            // Puedes añadir más lógica aquí si tu 'principal' tiene otra estructura para obtener el ID del usuario
        }

        logger.info("Intento de descarga de factura para reserva ID: {} por usuario ID autenticado: {}", idReserva, idUsuarioAutenticado);

        try {
            // Llama a tu servicio para obtener el DTO, pasando el ID del usuario autenticado
            FacturaDTO facturaDTO = facturaService.generarFacturaDTO(idReserva, idUsuarioAutenticado);
            // Llama a tu servicio para generar el PDF
            byte[] pdfBytes = facturaService.generarFacturaPDF(facturaDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "factura_reserva_" + idReserva + ".pdf";
            headers.setContentDispositionFormData("attachment", filename); // Esto fuerza la descarga en el navegador
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            logger.info("Factura PDF generada y lista para descarga para reserva ID: {}", idReserva);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            // Manejo específico de excepciones de validación (ej. 404 NOT_FOUND, 403 FORBIDDEN, 400 BAD_REQUEST)
            logger.warn("Error controlado al descargar factura para reserva ID {}: {} - {}", idReserva, e.getStatusCode(), e.getReason());
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            // Manejo general de otros errores inesperados durante la generación del PDF
            logger.error("Error inesperado al descargar factura para reserva ID {}: {}", idReserva, e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}