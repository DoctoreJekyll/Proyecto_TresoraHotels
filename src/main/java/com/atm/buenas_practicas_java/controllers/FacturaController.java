package com.atm.buenas_practicas_java.controllers;


import com.atm.buenas_practicas_java.entities.Factura;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.FacturaService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.MetodoPagoService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/factura")

public class FacturaController {

    private final FacturaService facturaService;
    private final HotelService hotelService;
    private final UsuarioService usuarioService;
    private final MetodoPagoService metodoPagoService;

    public FacturaController(FacturaService facturaService, HotelService hotelService, UsuarioService usuarioService, MetodoPagoService metodoPagoService) {
        this.facturaService = facturaService;
        this.hotelService = hotelService;
        this.usuarioService = usuarioService;
        this.metodoPagoService = metodoPagoService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioFactura (Model model) {
        Factura factura = new Factura();
        model.addAttribute("factura", factura);

        List<Hotel> hoteles = hotelService.findAll();
        model.addAttribute("hoteles", hoteles);

        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);

        List<MetodoPago> metodosPagos = metodoPagoService.findAll();
        model.addAttribute("metodosPagos", metodosPagos);

        return "facturaForm";
    }

    @PostMapping("/guardar")
    public String guardarFactura(@ModelAttribute Factura factura) {
        facturaService.save(factura);
        return "redirect:/lista/factura";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioFacturasEditar (@PathVariable Integer id, Model model) {
        Factura factura = facturaService.findByIdWithRelations(id)
                .orElseThrow (()-> new RuntimeException("Factura no encontrada"));
        model.addAttribute("factura", factura);
        return "facturaForm";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarFactura(@PathVariable Integer id) {
        facturaService.deleteById(id);
        return "redirect:/lista/factura";
    }
}
