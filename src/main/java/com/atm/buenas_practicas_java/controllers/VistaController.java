package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.controllers.Factory.FactoryList;
import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lista")
public class VistaController {
    private final FactoryList  factoryList;

    public VistaController(FactoryList factoryList) {
        this.factoryList = factoryList;
    }

    @GetMapping("/{entityName}")
    public String listarDatos(@PathVariable String entityName, Model model)
    {
        IFactoryProvider factoryProvider = factoryList.getFactory(entityName)
                .orElseThrow(() -> new RuntimeException("No existe el Factory"));

        model.addAttribute("titulo", factoryProvider.getTitles());
        model.addAttribute("cabeceras", factoryProvider.getHeaders());
        model.addAttribute("filas", factoryProvider.getRows());
        model.addAttribute("rutaEntidad", factoryProvider.getEntityName());
        return "vistaLista";
    }
}
//Ahorramos html