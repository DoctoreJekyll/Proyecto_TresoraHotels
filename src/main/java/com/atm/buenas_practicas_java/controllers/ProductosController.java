package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.CategoriaProducto;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.entities.Producto;
import com.atm.buenas_practicas_java.services.CategoriaProductoService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/productos")
public class ProductosController {
    private final ProductoService productoService;
    private final CategoriaProductoService categoriaProductoService;
    private final HotelService hotelService;

    public ProductosController(ProductoService productoService, CategoriaProductoService categoriaProductoService, HotelService hotelService) {
        this.productoService = productoService;
        this.categoriaProductoService = categoriaProductoService;
        this.hotelService = hotelService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioProductos(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);

        List<CategoriaProducto> categoriaProducto = categoriaProductoService.findAll();
        model.addAttribute("categoriaProducto", categoriaProducto);

        List<Hotel> hotel = hotelService.getRepo().findAll();
        model.addAttribute("hotel", hotel);

        model.addAttribute("categorias", categoriaProductoService.findAll());
        model.addAttribute("hoteles", hotelService.findAll());
        return "productosForm";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.save(producto);
        return "redirect:/lista/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Producto producto = productoService.findByIdWithCategoria(id).orElseThrow();


        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaProductoService.findAll());
        model.addAttribute("hoteles", hotelService.findAll());

        return "productosForm";
    }

    @PostMapping("eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoService.deleteById(id);
        return "redirect:/lista/productos";
    }

}
