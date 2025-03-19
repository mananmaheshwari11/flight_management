package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @Column(length = 6,unique=true)
    private String ticket_id =UUID.randomUUID().toString().replaceAll("[^A-Za-z0-9]", "").substring(0, 6);;

    private Long userId;
    private Long flightId;

    private Double ticket_price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime jouneydate;

    private LocalDateTime created_at;

}
