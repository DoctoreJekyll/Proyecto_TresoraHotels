package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.MetodoPago;
import com.atm.buenas_practicas_java.services.CategoriaProductoService;
import com.atm.buenas_practicas_java.services.MetodoPagoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/metodopago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }


    @GetMapping("/nuevo")
    public String mostrarFormularioMetodoPago(Model model)
    {
      MetodoPago metodoPago = new MetodoPago();
      model.addAttribute("metodoPago", metodoPago);
      return "metodoPagoForm";

    }

    @PostMapping("/guardar")
    public String guardarMetodoPago(@ModelAttribute MetodoPago metodoPago)
    {
        metodoPagoService.save(metodoPago);
        return "redirect:/lista/metodopago";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model){
        MetodoPago metodoPago = metodoPagoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        model.addAttribute("metodoPago", metodoPago);
        return "metodoPagoForm";

        //       Optional<MetodoPago> metodoPago = metodoPagoService.findById(id);
//        MetodoPago metodoPago1 = metodoPago.get();
//        model.addAttribute("metodoPago", metodoPago1);
    }
    @PostMapping("eliminar/{id}")
    public String eliminarMetodoPago (@PathVariable Integer id) {
        metodoPagoService.deleteById(id);
        return "redirect:/lista/metodopago";
    }
}
