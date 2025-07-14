//package com.atm.buenas_practicas_java.controllers;
//
//import com.atm.buenas_practicas_java.DTOs.HotelViewDTO;
//import com.atm.buenas_practicas_java.entities.Habitacion;
//import com.atm.buenas_practicas_java.entities.Hotel;
//import com.atm.buenas_practicas_java.services.HotelService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.*;
//
//@Controller
//@RequestMapping("/home")
//public class HotelController {
//
//    private final HotelService hotelService;
//    public HotelController(HotelService hotelService) {
//        this.hotelService = hotelService;
//    }
//
//    @GetMapping({"", "/"})
//    public String mostrarHoteles(Model model) {
//        List<Hotel> hotels = hotelService.findAll();
//        model.addAttribute("hotels", hotels);
//        return "home";
//    }
//
//
//
//
//}
