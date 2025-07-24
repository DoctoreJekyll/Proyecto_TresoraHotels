package com.atm.buenas_practicas_java.services.reserva;

import com.atm.buenas_practicas_java.entities.Rol;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.repositories.UsuarioRepo;
import com.atm.buenas_practicas_java.services.EmailService;
import com.atm.buenas_practicas_java.services.RolService;
import com.atm.buenas_practicas_java.util.PasswordGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioTemporalService {

    private final RolService rolService;
    private final EmailService emailService;
    private final UsuarioRepo usuarioRepo;

    public UsuarioTemporalService(RolService rolService, EmailService emailService, UsuarioRepo usuarioRepo) {
        this.rolService = rolService;
        this.emailService = emailService;
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Busca un usuario por email o lo crea si no existe, con contraseña generada y rol por defecto.
     * @param nombre Nombre del usuario.
     * @param email Email del usuario.
     * @return Usuario existente o nuevo.
     */
    @Transactional
    public Usuario findOrCreateUser(String nombre, String email) {
        return usuarioRepo.findByEmail(email)
                .orElseGet(() -> {
                    Usuario newUser = new Usuario();
                    newUser.setNombre(nombre);
                    newUser.setEmail(email);
                    newUser.setPassword(PasswordGenerator.generateRandomPassword());

                    Rol defaultRole = rolService.findById(1)
                            .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
                    newUser.setIdRol(defaultRole);

                    return usuarioRepo.save(newUser);
                });
    }

    private void enviarEmailBienvenida(Usuario usuario, String passwordPlano) {
        String contenido = String.format("""
                Hola %s,

                Hemos creado un usuario temporal en nuestra base de datos para que puedas acceder a tu perfil y ver tu reserva.
                Tus datos de acceso son:

                Usuario (Email): %s
                Contraseña temporal: %s

                Por favor, cambia tu contraseña al iniciar sesión por primera vez.

                ¡Esperamos verte pronto!
                """, usuario.getNombre(), usuario.getEmail(), passwordPlano);

        emailService.sendEmail(
                "notificaciones@agestturnos.es",
                usuario.getEmail(),
                "¡Bienvenido a nuestro hotel! Tu acceso temporal",
                contenido
        );
    }
}

