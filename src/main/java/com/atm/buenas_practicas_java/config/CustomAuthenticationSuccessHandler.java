package com.atm.buenas_practicas_java.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isCliente = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));
        boolean isAdminEmpLimpieza = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        a.getAuthority().equals("ROLE_EMPLEADO") ||
                        a.getAuthority().equals("ROLE_LIMPIEZA"));

        if (isCliente) {
            response.sendRedirect("/usuarios/userhome");
        } else if (isAdminEmpLimpieza) {
            response.sendRedirect("/panel");
        } else {
            // Por si no tiene ningún rol esperado, redirigir a una página por defecto o error
            response.sendRedirect("/login?error");
        }
    }
}

