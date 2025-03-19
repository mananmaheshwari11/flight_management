package org.example.flights.repository;


import org.example.flights.dto.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flights,Long> {
    List<Flights> findByDeparturetimeBetween(LocalDateTime from, LocalDateTime to);
    List<Flights> findByDepartureAndDestination(String departure, String destination);
//    List<Flights> findByFlightnameRegex(String flightname);

}

