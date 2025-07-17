package com.atm.buenas_practicas_java.services.converters;

import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.UsuarioService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UsuariosConverter implements Converter<Integer, Usuario> {


    private final UsuarioService usuarioService;

    public UsuariosConverter(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Usuario convert(Integer source) {
        return usuarioService.getRepository().findById(source).orElseThrow();
    }
}
