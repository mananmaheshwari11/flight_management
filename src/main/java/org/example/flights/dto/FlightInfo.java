package org.example.flights.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Data
public class FlightInfo {
    public int status;
    public String message;
    public List<Flights> flightsList;
    public Optional<Flights> flight;

    public  FlightInfo(int status, String message, List<Flights> flightsList) {
        this.status = status;
        this.message = message;
        this.flightsList = flightsList;
        this.flight = null;
    }

    public FlightInfo(int status,String message, Optional<Flights> flight) {
        this.status = status;
        this.message = message;
        this.flight = flight;
        this.flightsList = null;
    }
}
