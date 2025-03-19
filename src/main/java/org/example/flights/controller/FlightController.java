package org.example.flights.controller;

import jakarta.validation.Valid;
import org.example.flights.dto.FlightInfo;
import org.example.flights.dto.Flights;
import org.example.flights.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping("/")
    public ResponseEntity<String> createFlight(@Valid @RequestBody Flights flight){
        return flightService.addFlight(flight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id){
        return flightService.deleteFlight(id);
    }

    @GetMapping("/")
    public ResponseEntity<FlightInfo> getFlights(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String source,
        @RequestParam(required = false) String destination,
//        @RequestParam(required = false) String query,
        @RequestParam(required = false) String date
    ){

        if (id!=null){
            return flightService.getFlightById(id);
        }

        if(source!=null && destination!=null){
            return  flightService.getFlightByPlaces(source,destination);
        }

        if(date!=null){
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime start = localDate.atStartOfDay();
            LocalDateTime end = localDate.atTime(LocalTime.MAX);
            return flightService.getFlightsByDate(start,end);
        }
        return flightService.getAllFlights();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateSeat(@PathVariable Long id , @RequestParam String action){
        if(action.equalsIgnoreCase("increase")){
            return flightService.increaseSeat(id);
        }
        else{
            return flightService.decreaseSeat(id);
        }
    }




}
