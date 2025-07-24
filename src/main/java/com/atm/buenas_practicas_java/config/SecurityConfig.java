package com.atm.buenas_practicas_java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Utiliza las anotaciones de Spring Security para definir y personalizar
 * la seguridad de la aplicación, incluyendo la configuración de solicitudes
 * HTTP, autenticación y control de acceso.
 *
 * @Configuration Marca esta clase como una clase de configuración.
 * @EnableWebSecurity Habilita la seguridad web de Spring Security en la aplicación.
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler successHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .csrf(Customizer.withDefaults()) // deshabilitado para pruebas o APIs
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureUrl("/login-error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/logout-exito");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .authorizeHttpRequests(auth -> auth
                        // Recursos públicos
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/home", "/login", "/login-error", "/logout-exito", "/usuarios/crear-cuenta").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/reserva/rapida").permitAll()
                        .requestMatchers("/contactos/nuevo").permitAll()
                        .requestMatchers("/hoteles").permitAll()
                        .requestMatchers("/reserva/api/**").permitAll()

                        // Rutas específicas por rol
                        .requestMatchers("/panel").hasAnyRole("ADMIN", "EMPLEADO", "LIMPIEZA")
                        .requestMatchers("/lista/usuarios").hasAnyRole("ADMIN", "EMPLEADO")
                        .requestMatchers("/lista/facturas").hasAnyRole("ADMIN", "EMPLEADO")
                        .requestMatchers("/lista/reservas").hasAnyRole("ADMIN", "EMPLEADO")
                        .requestMatchers("/lista/informes").hasAnyRole("ADMIN", "EMPLEADO", "LIMPIEZA")
                        .requestMatchers("/informes/nuevo").hasAnyRole("ADMIN", "EMPLEADO", "LIMPIEZA")
                        .requestMatchers("/lista/habitaciones", "/lista/productos", "/lista/metodopago", "/lista/categorias").hasRole("ADMIN")


                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated());

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
