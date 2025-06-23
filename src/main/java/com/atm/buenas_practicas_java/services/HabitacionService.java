package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.repositories.HabitacionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {
    private final HabitacionRepo habitacionRepo;
    public HabitacionService(HabitacionRepo habitacionRepo) {
        this.habitacionRepo = habitacionRepo;
    }

    public List<Habitacion> findAllHabitaciones() {
        return habitacionRepo.findAll();
    }

    public Optional<Habitacion> findHabitacionById(Integer id) {
        return habitacionRepo.findById(id);
    }

    public void saveHabitacion(Habitacion habitacion) {
        habitacionRepo.save(habitacion);
    }

    public void deleteHabitacion(Habitacion habitacion) {
        habitacionRepo.delete(habitacion);
    }


}
