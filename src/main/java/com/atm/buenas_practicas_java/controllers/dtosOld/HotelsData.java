package com.atm.buenas_practicas_java.controllers.dtosOld;

import java.util.ArrayList;
import java.util.List;

public class HotelsData {

    public static List<HotelDto> hotelsDataList()
    {
        List<HotelDto> list = new ArrayList<>();

        list.add(new HotelDto(
                "Hotel Montaña Abajo",
                "Barcelona",
                "Paraiso montaña | Gran ubicación.",
                89.99,
                "Hotel 1 es una de las mejores opciones para disfrutar de una estancia en Barcelona.",
                "https://picsum.photos/600/400?random=5",

                List.of("Montañas pequeñas", "Escalada divertida", "Helicoptero privado"),
                "https://maps.googleapis.com/maps/api/staticmap?center=40.416775,-3.703790&zoom=14&size=600x400&markers=color:red%7C40.416775,-3.703790&key=AIzaSyCVai7Nwhhyg1XZVYa5ErxR_iRF0gzafGI"
        ));

        list.add(new HotelDto(
                "Hotel La playa tu vecina",
                "Madrid",
                "Playa gigante | Plaza Mayor.",
                99.99,
                "Hotel La playa de tu vecina es una gran opcion playera.",
                "https://picsum.photos/600/400?random=2",

                List.of("Playa a 15 minutos", "Piscina monstruosa", "Barco propio"),
                "https://maps.googleapis.com/maps/api/staticmap?center=48.856613,2.352222&zoom=14&size=600x400&markers=color:red%7C48.856613,2.352222&key=AIzaSyCVai7Nwhhyg1XZVYa5ErxR_iRF0gzafGI"

        ));

        list.add(new HotelDto(
                "Hotel Ciudad de humo",
                "Sevilla",
                "Paraiso en la ciudad | Ubicacion apasionante.",
                30,
                "Hotel de la ciudad es una de las mejores opciones para disfrutar de una estancia en Sevilla.",
                "https://picsum.photos/600/400?random=7",

                List.of("No tenemos playa", "No tenemos piscina", "Tenemos un estanco cerca"),
                "https://maps.googleapis.com/maps/api/staticmap?center=34.052235,-118.243683&zoom=14&size=600x400&markers=color:red%7C34.052235,-118.243683&key=AIzaSyCVai7Nwhhyg1XZVYa5ErxR_iRF0gzafGI"

        ));

        return list;
    }

}
