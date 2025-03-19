package org.example.flights.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
@Entity
@Table(name = "flights")
public class Flights {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long flight_id;
    @NotBlank
    private String flightname;
    @NotBlank
    private String departure;
    @NotBlank
    private String destination;

    @NotNull(message = "Departure time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departuretime;

    @NotNull(message = "Arrival time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivaltime;

    @Min(value = 0,message = "Flight seat cannot be negative")
    private int flightseats;

    @Min(value = 100, message = "Flight price must be at least 100")
    private double flightprice;

}
