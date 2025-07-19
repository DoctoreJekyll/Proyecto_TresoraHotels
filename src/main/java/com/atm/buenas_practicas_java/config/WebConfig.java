package com.atm.buenas_practicas_java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        "classpath:/static/images/",  // <--- agrega esto
                        "file:///C:/Users/jarma/Desktop/Proyecto_TresoraHotels/opt/imagenes/"  // este es el externo
                );
    }
}
