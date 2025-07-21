package com.atm.buenas_practicas_java.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("codigo", statusCode);

            // Puedes usar lógica según código
            if (statusCode == 404) {
                return "error/404";
            } else if (statusCode == 500) {
                return "error/500";
            } else if (statusCode == 403) {
                return "error/403";
            }
        }

        return "error/error"; // fallback genérico
    }
}
