package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Rol;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.RolService;
import com.atm.buenas_practicas_java.services.UsuarioService;
import com.atm.buenas_practicas_java.services.reserva.ReservaServiceRefactor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final HotelService hotelService;
    private final ReservaServiceRefactor reservaService;

    @GetMapping("/crear-cuenta")
    @PreAuthorize("permitAll()")
    public String mostrarFormularioRegistro(Model model, Authentication auth) {
        model.addAttribute("userData", new UsuarioDTO());

        boolean esAdmin = auth != null && auth.isAuthenticated()
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (esAdmin) {
            model.addAttribute("roles", rolService.findAll());
        }

        return "crearCuenta";
    }

    @PostMapping("/crear-cuenta")
    @PreAuthorize("permitAll()")
    public String procesarFormularioRegistro(@ModelAttribute("userData") UsuarioDTO dto,
                                             @RequestParam(name = "rolId", required = false) Integer rolId,
                                             BindingResult result,
                                             Model model,
                                             Authentication auth) {

        boolean esAdmin = auth != null && auth.isAuthenticated()
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        try {
            usuarioService.registrarUsuario(dto, rolId, esAdmin);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.userData", e.getMessage());
            if (esAdmin) {
                model.addAttribute("roles", rolService.findAll());
            }
            return "crearCuenta";
        }

        return "redirect:/login";
    }

    @GetMapping("/userhome")
    public String userHomePage(Model model, Authentication auth) {
        Usuario usuario = usuarioService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("hoteles", hotelService.findAll());
        return "user_home_page";
    }

    @GetMapping("/miPerfil")
    @PreAuthorize("isAuthenticated()")
    public String mostrarPerfilAutenticado(Model model, Authentication auth) {
        Usuario usuario = usuarioService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "miPerfil";
    }

    @PostMapping("/actualizar")
    @PreAuthorize("isAuthenticated()")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario formUsuario,
                                   BindingResult result, Model model,
                                   Authentication auth) {
        if (result.hasErrors()) return "miPerfil";

        Usuario actual = usuarioService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Usuario objetivo = usuarioService.findEntity(formUsuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuarioService.puedeModificar(actual, objetivo)) {
            throw new AccessDeniedException("No tienes permiso");
        }

        objetivo.setNombre(formUsuario.getNombre());
        objetivo.setApellidos(formUsuario.getApellidos());
        objetivo.setEmail(formUsuario.getEmail());
        objetivo.setTelefono(formUsuario.getTelefono());
        objetivo.setDireccion(formUsuario.getDireccion());
        objetivo.setDni(formUsuario.getDni());
        objetivo.setFechaNacimiento(formUsuario.getFechaNacimiento());

        usuarioService.saveEntity(objetivo);
        model.addAttribute("usuario", objetivo);
        model.addAttribute("successMessage", "Perfil actualizado correctamente");
        return "miPerfil";
    }

    @PostMapping("/eliminar")
    @PreAuthorize("isAuthenticated()")
    public String eliminarCuenta(@ModelAttribute("usuario") Usuario usuario, Authentication auth) {
        Usuario actual = usuarioService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Usuario objetivo = usuarioService.findEntity(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuarioService.puedeModificar(actual, objetivo)) {
            throw new AccessDeniedException("No tienes permiso");
        }

        usuarioService.deleteById(usuario.getId());
        return "redirect:/home";
    }

    @GetMapping("/cambiar-contrasena")
    @PreAuthorize("isAuthenticated()")
    public String mostrarFormularioCambioContrasena() {
        return "cambiarContrasena";
    }

    @PostMapping("/cambiar-contrasena")
    @PreAuthorize("isAuthenticated()")
    public String cambiarContrasena(@RequestParam String actual,
                                    @RequestParam String nueva,
                                    @RequestParam String confirmacion,
                                    Model model,
                                    Authentication auth) {
        Usuario usuario = usuarioService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuarioService.verificarPassword(usuario, actual)) {
            model.addAttribute("errorMessage", "La contraseña actual no es correcta.");
            return "cambiarContrasena";
        }

        if (!nueva.equals(confirmacion)) {
            model.addAttribute("errorMessage", "La nueva contraseña no coincide con la confirmación.");
            return "cambiarContrasena";
        }

        usuarioService.actualizarPassword(usuario, nueva);
        model.addAttribute("successMessage", "Contraseña actualizada correctamente.");
        return "cambiarContrasena";
    }


    //--------------------------------CONTROLLER DE VISTAS DE EMPLEADO----------------------------------------------//

    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String nuevoUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.findAll()); // para el desplegable de roles
        return "usuarioForm";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String editarUsuario(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.findEntity(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.findAll());
        return "usuarioForm";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                 @RequestParam("rolId") Integer rolId) {
        Rol rol = rolService.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setIdRol(rol);
        usuarioService.saveEntity(usuario);
        return "redirect:/lista/usuarios";
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        usuarioService.deleteById(id);
        return "redirect:/lista/usuarios";
    }

    //--------------------------------CONTROLLER DE VISTAS USUARIOS LOG/RESERVAS----------------------------------------------//

    @GetMapping("/reserva/{id}")
    public String usuarioListaReservas(@PathVariable("id") Integer id, Model model, Authentication auth) {
        Usuario usuario = usuarioService.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        model.addAttribute("reserva", reservaService.findReservaByUsuario(usuario.getId()));
        return "usuarioListaReservas";
    }

}



