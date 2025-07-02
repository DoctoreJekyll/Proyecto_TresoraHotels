package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.services.CategoriaProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

    private final CategoriaProductoService categoriaProductoService;

    public CategoriasController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCategorias(Model model)
    {
        CategoriaProducto categoria = new CategoriaProducto();
        model.addAttribute("categoria", categoria);
        return "categoriaForm";
    }


    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute CategoriaProducto categoria)
    {
        categoriaProductoService.save(categoria);
        return "redirect:/lista/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        CategoriaProducto categoria = categoriaProductoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        model.addAttribute("categoria", categoria);
        return "categoriaForm";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        categoriaProductoService.deleteById(id);
        return "redirect:/lista/categorias";
    }

}
