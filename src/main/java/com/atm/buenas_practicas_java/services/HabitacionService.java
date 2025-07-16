package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.repositories.HabitacionRepo;
import com.atm.buenas_practicas_java.services.templateMethod.AbstractTemplateServicesEntities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService extends AbstractTemplateServicesEntities<Habitacion, Integer, HabitacionRepo> {

    public HabitacionService(HabitacionRepo habitacionRepo) {
        super(habitacionRepo);
    }

    public Optional<Habitacion> findByIdWithHotelAndProducto(Integer id) {
        return getRepo().findByIdWithHotelAndProducto(id);
    }

    public List<Habitacion> findAllConHotel() {
        return getRepo().findAllWithHotel();
    }

    @Transactional
    public List<Habitacion> findByHotelId(Integer hotelId) {
        return getRepo().findByHotelId(hotelId);
    }
    public List<Habitacion> findByHotelIdAndOcupada(Integer hotelId) {
        return getRepo().findByHotelIdAndOcupadaFalse(hotelId);
    }

    public Page<Habitacion> findByHotelId(Integer hotelId, Pageable pageable) {
        return getRepo().findByHotelId(hotelId, pageable);
    }

    public List<Habitacion> findDisponiblesPorHotelYFechas(Integer hotelId, LocalDate entrada, LocalDate salida) {
        return getRepo().findDisponiblesPorHotelYFechas(hotelId, entrada, salida);
    }

    public List<Habitacion> obtenerDisponiblesPorHotelYFechas(Integer hotelId, String fechaEntrada, String fechaSalida) {
        LocalDate entrada = LocalDate.parse(fechaEntrada);
        LocalDate salida = LocalDate.parse(fechaSalida);
        return getRepo().findDisponiblesPorHotelYFechas(hotelId, entrada, salida);
    }

    // NUEVO MÉTODO para paginar habitaciones por hotel e IDs específicos
    @Transactional(readOnly = true)
    public Page<Habitacion> findByHotelIdAndIdsIn(Integer hotelId, List<Integer> habitacionIds, Pageable pageable) {
        return getRepo().findByHotelIdAndIdIn(hotelId, habitacionIds, pageable);
    }


}
