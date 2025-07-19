package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTOs.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.Rol;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ReservaService;
import com.atm.buenas_practicas_java.services.RolService;
import com.atm.buenas_practicas_java.services.UsuarioService;
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
    private final ReservaService reservaService;

    @GetMapping("/crear-cuenta")
    @PreAuthorize("permitAll()")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("userData", new UsuarioDTO());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
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

        // Validaciones
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.userData", "Las contraseñas no coinciden");
        }

        if (usuarioService.findAllEntities().stream().anyMatch(u -> u.getEmail().equals(dto.getEmail()))) {
            result.rejectValue("email", "error.userData", "El email ya está registrado");
        }

        if (result.hasErrors()) {
            if (auth != null && auth.isAuthenticated()
                    && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("roles", rolService.findAll());
            }
            return "crearCuenta";
        }

        // Conversión a entidad y guardado
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // Se cifrará en el servicio
        usuario.setFechaAlta(java.time.LocalDate.now());
        usuario.setActivo(true);


        // Rol por defecto o elegido
        Rol rol;
        if (auth != null && auth.isAuthenticated()
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) && rolId != null) {
            rol = rolService.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no válido"));
        } else {
            rol = rolService.findByNombre("CLIENTE").orElseThrow(() -> new RuntimeException("Rol cliente no encontrado"));
        }

        usuario.setIdRol(rol);

        usuarioService.saveEntity(usuario);

        return "redirect:/login";
    }

    @GetMapping("/userhome")
    public String userHomePage(Model model, Authentication auth) {
        String email = auth.getName();
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("hoteles", hotelService.findAll());
        return "user_home_page";
    }

    @GetMapping("/miPerfil")
    @PreAuthorize("isAuthenticated()")
    public String mostrarPerfilAutenticado(Model model, Authentication auth) {
        String email = auth.getName();
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "miPerfil";
    }

    @PostMapping("/actualizar")
    @PreAuthorize("isAuthenticated()")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario formUsuario,
                                   BindingResult result, Model model,
                                   Authentication auth) {
        if (result.hasErrors()) {
            return "miPerfil";
        }

        Usuario usuarioExistente = usuarioService.findEntity(formUsuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!auth.getName().equals(usuarioExistente.getEmail())
                && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("No tienes permiso para editar este perfil");
        }

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
    @PreAuthorize("isAuthenticated()")
    public String eliminarCuenta(@ModelAttribute("usuario") Usuario usuario, Authentication auth) {
        Usuario usuarioExistente = usuarioService.findEntity(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!auth.getName().equals(usuarioExistente.getEmail())
                && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("No tienes permiso para eliminar esta cuenta");
        }

        usuarioService.deleteById(usuario.getId());
        return "redirect:/home";
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
    public String usuarioListaReservas(@PathVariable("id") Integer id, Model model)
    {
        Usuario usuario;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String userEmail = user.getUsername();

            usuario = usuarioService.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            model.addAttribute("reserva", reservaService.findReservaByUsuario(usuario.getId()));
            return "usuarioListaReservas";
        }
        else
        {
            return "redirect:/user_home_page";
        }

    }

}



