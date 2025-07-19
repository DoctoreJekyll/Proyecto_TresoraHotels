package com.atm.buenas_practicas_java.controllers.FactoryImpl;

import com.atm.buenas_practicas_java.controllers.Factory.IFactoryProvider;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.UsuarioService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsuariosFactoryImpl implements IFactoryProvider {

    private final UsuarioService usuarioService;

    public UsuariosFactoryImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public String getTitles() {
        return "Lista de Usuarios";
    }

    @Override
    public List<String> getHeaders() {
        return List.of("id", "nombre", "apellidos", "email", "rol", "activo");
    }

    @Override
    public List<Map<String, Object>> getRows() {
        List<Usuario> usuarios = usuarioService.findAllEntities();

        return usuarios.stream().map(usuario -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", usuario.getId());
            row.put("nombre", usuario.getNombre());
            row.put("apellidos", usuario.getApellidos());
            row.put("email", usuario.getEmail());
            row.put("rol", usuario.getIdRol().getNombreRol());
            row.put("activo", usuario.getActivo() != null && usuario.getActivo() ? "Sí" : "No");
            return row;
        }).toList();
    }

    @Override
    public String getEntityName() {
        return "usuarios";
    }
}
