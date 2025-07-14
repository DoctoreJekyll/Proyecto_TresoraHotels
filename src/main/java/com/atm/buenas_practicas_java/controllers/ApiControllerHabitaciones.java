package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.services.HabitacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/habitaciones")
public class ApiControllerHabitaciones {

    private final HabitacionService habitacionService;
    public ApiControllerHabitaciones(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> getHabitacion(@PathVariable Integer id) {
        Optional<Habitacion> habitacionOpt = habitacionService.findById(id);
        Habitacion habitacion = habitacionOpt.orElseThrow();
        return new ResponseEntity<>(habitacion, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Habitacion>> getAllHabitacion() {
        List<Habitacion> habitaciones = habitacionService.findAll();
        return new ResponseEntity<>(habitaciones, HttpStatus.OK);
    }

    @GetMapping("recents/{page}")
    public ResponseEntity<List<Habitacion>> getAllHabitaciones(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("numeroHabitacion").descending());
        Page<Habitacion> habitacionList = habitacionService.findByHotelId(1,pageable);
        return new ResponseEntity<>(habitacionList.getContent(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Habitacion> createHabitacion(@RequestBody Habitacion habitacion) {
        Habitacion createdHabitacion = habitacionService.save(habitacion);
        if (createdHabitacion != null) {
            return new ResponseEntity<>(createdHabitacion, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Habitacion> updateHabitacion(@RequestBody Habitacion habitacion) {
        Habitacion savedHabitacion = habitacionService.save(habitacion);
        return new ResponseEntity<>(savedHabitacion, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Habitacion> deleteHabitacion(@RequestParam Integer id) {
        Optional<Habitacion> habitacionOpt = habitacionService.findById(id);
        if (habitacionOpt.isPresent()) {
            habitacionService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
