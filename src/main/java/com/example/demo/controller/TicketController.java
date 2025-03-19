package com.example.demo.controller;

import com.example.demo.dto.Ticket;
import com.example.demo.dto.TicketInfo;
import com.example.demo.services.TicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketServices ticketServices;

    @PostMapping("/")
    public ResponseEntity<TicketInfo> addTicket(
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) Long userId) {
        return ticketServices.addTicket(flightId,userId);
    }

    @GetMapping("/")
    public ResponseEntity<TicketInfo> viewTicket(
            @RequestParam(required = false) String ticket_id,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false)String journeyDate) {
        if(ticket_id!= null) {
            return ticketServices.getTicket(ticket_id);
        }
        if(userId!=null){
            if(flightId != null) {
                return ticketServices.findTicketByFlightId(flightId,userId);
            }
            if(journeyDate!= null) {
                LocalDate localDate = LocalDate.parse(journeyDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDateTime start = localDate.atStartOfDay();
                LocalDateTime end = localDate.atTime(LocalTime.MAX);
                return ticketServices.findTicketByJourneyDate(userId,start,end);
            }
            return ticketServices.findAllTickets(userId);
        }
        else{
            return ResponseEntity.status(400).body(new TicketInfo(400,"Insufficient details to GET",null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable String id){
        return ticketServices.deleteTicket(id);
    }
}
