package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.Contacto;
import com.atm.buenas_practicas_java.services.ContactoService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContactoFactoryImpl implements IFactoryProvider {

    private final ContactoService contactoService;

    public ContactoFactoryImpl(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @Override
    public String getTitles() {
        return "Entradas del formulario de contacto  ";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id","nombre","correo","telefono","departamento","mensaje","fechaEnvio","foto1","foto2");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<Contacto> contactos = contactoService.findAll();
        List<Map<String, Object>> rows = contactos.stream()
                .map(contacto -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", contacto.getId());
//                  row.put("idUsuario", contacto.getIdUsuario());
                    row.put("nombre", contacto.getNombre());
                    row.put("correo", contacto.getCorreo());
                    row.put("telefono", contacto.getTelefono());
                    row.put("departamento", contacto.getDepartamento());
                    row.put("mensaje", contacto.getMensaje());
                    row.put("fechaEnvio", contacto.getFechaEnvio());
                    row.put("foto1", contacto.getFoto1());
                    row.put("foto2", contacto.getFoto2());
                    return row;
                }).toList();
        return rows;
    }

    @Override
    public String getEntityName() {
        return "contactos";
    }
}
