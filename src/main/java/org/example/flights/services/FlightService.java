package org.example.flights.services;


import org.example.flights.dto.FlightInfo;
import org.example.flights.dto.Flights;
import org.example.flights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public ResponseEntity<String> addFlight(Flights flight) {
        flightRepository.save(flight);
        return ResponseEntity.ok("Flight Added Successfully");
    }
    public ResponseEntity<String> deleteFlight(Long id) {
        try{
        flightRepository.deleteById(id);
        return ResponseEntity.ok("Flight Deleted Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<FlightInfo> getAllFlights() {
        try{
        List<Flights> flights = flightRepository.findAll();
        FlightInfo response=new FlightInfo(200,"Flight List",flights);
        return ResponseEntity.ok(response);
        }
        catch(Exception e){
            FlightInfo response=new FlightInfo(400,e.getMessage(), (List<Flights>) null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    public ResponseEntity<FlightInfo> getFlightById(Long id) {
        try{
            Optional<Flights> flight=flightRepository.findById(id);
            FlightInfo response=new FlightInfo(200,"Flight List",flight);
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            FlightInfo response=new FlightInfo(400,e.getMessage(), Optional.ofNullable(null));
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<FlightInfo> getFlightByPlaces(String source, String destination) {
        List<Flights> flights=flightRepository.findByDepartureAndDestination(source, destination);
        FlightInfo response=new FlightInfo(200,"Flight List by source & destination",flights);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<FlightInfo> getFlightsByDate(LocalDateTime start, LocalDateTime end) {
        List<Flights> flights=flightRepository.findByDeparturetimeBetween(start, end);
        FlightInfo response=new FlightInfo(200,"Flight List by date",flights);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> increaseSeat(Long flight_id) {
        Optional<Flights> flights=flightRepository.findById(flight_id);
        flights.get().setFlightseats(flights.get().getFlightseats() + 1);
        flightRepository.save(flights.get());
        return ResponseEntity.ok("Flight Increased Successfully");
    }

    public ResponseEntity<String> decreaseSeat(Long flight_id) {
        Optional<Flights> flights=flightRepository.findById(flight_id);
        flights.get().setFlightseats(flights.get().getFlightseats() - 1);
        flightRepository.save(flights.get());
        return ResponseEntity.ok("Flight Increased Successfully");
    }

//    public ResponseEntity<FlightInfo> getFlightByQuery(String query) {
//        List<Flights> flights=flightRepository.findByFlightnameRegex(query);
//        FlightInfo response=new FlightInfo(200,"Flight List by query",flights);
//        return ResponseEntity.ok(response);
//    }
}
