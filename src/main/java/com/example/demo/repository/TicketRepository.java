package com.example.demo.repository;

import com.example.demo.dto.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByUserIdAndFlightId(Long userId, Long flightId);
    List<Ticket> findByUserIdAndJouneydateBetween(Long userId, LocalDateTime jouneyDate_start, LocalDateTime jouneyDate_end);
}
