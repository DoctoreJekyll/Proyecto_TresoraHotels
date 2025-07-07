package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Rol;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.RolService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    @GetMapping("/crear-cuenta")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("userData", new UsuarioDTO());
        return "crearCuenta";
    }

    @PostMapping("/crear-cuenta")
    public String procesarFormularioRegistro(@Valid @ModelAttribute("userData") UsuarioDTO dto,
                                             BindingResult result, Model model) {

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.userData", "Las contraseñas no coinciden");
        }

        // Email ya registrado
        if (usuarioService.findAllEntities().stream().anyMatch(u -> u.getEmail().equals(dto.getEmail()))) {
            result.rejectValue("email", "error.userData", "El email ya está registrado");
        }

        if (result.hasErrors()) {
            return "crearCuenta";
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // 🔐 Recuerda cifrar en producción
        usuario.setFechaAlta(LocalDate.now());
        usuario.setActivo(true);

        Rol rol = rolService.findByNombre("cliente")
                .orElseThrow(() -> new RuntimeException("Rol cliente no encontrado"));
        usuario.setIdRol(rol);

        usuarioService.saveEntity(usuario);

        return "redirect:/login";
    }

    @GetMapping("/miPerfil/{id}")
    public String mostrarPerfil(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.findEntity(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "miPerfil";
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario formUsuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "miPerfil";
        }

        Usuario usuarioExistente = usuarioService.findEntity(formUsuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(formUsuario.getNombre());
        usuarioExistente.setApellidos(formUsuario.getApellidos());
        usuarioExistente.setEmail(formUsuario.getEmail());
        usuarioExistente.setTelefono(formUsuario.getTelefono());
        usuarioExistente.setDireccion(formUsuario.getDireccion());
        usuarioExistente.setDni(formUsuario.getDni());
        usuarioExistente.setFechaNacimiento(formUsuario.getFechaNacimiento());

        usuarioService.saveEntity(usuarioExistente);

        model.addAttribute("usuario", usuarioExistente);
        model.addAttribute("successMessage", "Perfil actualizado correctamente");

        return "miPerfil";
    }

    @PostMapping("/eliminar")
    public String eliminarCuenta(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.deleteById(usuario.getId());
        return "redirect:/home";
    }
}


