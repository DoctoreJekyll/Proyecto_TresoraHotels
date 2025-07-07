package com.atm.buenas_practicas_java.controllers.facades;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.services.EmailService;
import com.atm.buenas_practicas_java.services.HabitacionService;
import com.atm.buenas_practicas_java.services.HotelService;
import com.atm.buenas_practicas_java.services.ProductoService;
import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class HabitacionesFacade {
    private final HabitacionService habitacionService;
    private final IUploadFilesService uploadFilesService;

    public void findAllHabitaciones(){
        habitacionService.findAll();
    }

}
