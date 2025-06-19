package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTOs.HotelViewDTO;
import com.atm.buenas_practicas_java.entities.Habitacion;
import com.atm.buenas_practicas_java.entities.Hotel;
import com.atm.buenas_practicas_java.repositories.HotelesRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HotelService {

    private final HotelesRepo hotelesRepo;
    public HotelService(HotelesRepo hotelesRepo) {
        this.hotelesRepo = hotelesRepo;
    }

    public List<Hotel> getHotels() {
        return hotelesRepo.findAll();
    }

    public Optional<Hotel> getHotel(int id) {
        return hotelesRepo.findById(id);
    }

    public void saveHotel(Hotel hotel) {
        hotelesRepo.save(hotel);
    }

    public void deleteHotel(int id) {
        hotelesRepo.deleteById(id);
    }

    public List<HotelViewDTO> obtenerHotelesConPrecioMedio() {
        List<Hotel> hoteles = getHotels(); // obtiene todos los hoteles
        List<HotelViewDTO> hotelesConPrecio = new ArrayList<>();

        for (Hotel hotel : hoteles) {
            double precioMedio = calcularPrecioMedioHabitacionesPorHotel(hotel.getId());
            hotelesConPrecio.add(new HotelViewDTO(hotel, precioMedio));
        }

        return hotelesConPrecio;
    }


    public double calcularPrecioMedioHabitacionesPorHotel(Integer hotelId) {
        Optional<Hotel> optionalHotel = getHotel(hotelId);

        if (optionalHotel.isEmpty()) {
            throw new IllegalArgumentException("Hotel no encontrado con id: " + hotelId);
        }

        Hotel hotel = optionalHotel.get();
        Set<Habitacion> habitaciones = hotel.getHabitaciones();

        if (habitaciones == null || habitaciones.isEmpty()) {
            return 0;
        }

        double suma = 0;
        int contador = 0;

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdProducto() != null) {
                suma += habitacion.getIdProducto().getPrecioBase();
                contador++;
            }
        }

        return contador == 0 ? 0 : suma / contador;
    }



}
