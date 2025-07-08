package com.atm.buenas_practicas_java.controllers;


import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/facturas")

public class FacturaController {

    private final FacturaService facturaService;
    private final HotelService hotelService;
    private final UsuarioService usuarioService;
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
        if (factura.getEstado().contains("pagada")) {
            msg = "Factura pagada con éxito";
        } else {
            msg = "Factura pendiente";
        }
        emailService.sendEmail(
                "notificaciones@agestturnos.es",
                "alba2gr@gmail.com",
                "Factura registrada",
                "Estado de la factura : " + msg);
        System.out.println("Email enviado");
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

}
