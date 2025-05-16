package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServices {
    @Autowired
    public TicketRepository ticketRepository;

    @Autowired
    public RestTemplate restTemplate;

    @Value("${user.service.url}")
    private  String user_url;

    @Value("${flight.service.url}")
    private String flight_url;

    public ResponseEntity<TicketInfo> addTicket(Long flight_id, Long user_id) {
            ResponseEntity<UserInfo> userInfo = restTemplate.getForEntity(user_url+user_id, UserInfo.class);
            if(!(userInfo.getStatusCode() == HttpStatus.OK)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            ResponseEntity<FlightInfo> flightInfo = restTemplate.getForEntity(flight_url+"?id="+flight_id, FlightInfo.class);
            if(!(flightInfo.getStatusCode() == HttpStatus.OK)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            }
//            System.out.println(flightInfo);
//        System.out.println("We are the middle after rest");
            Flights flight = flightInfo.getBody().flight.get();
            UserInfo user = userInfo.getBody();
            System.out.println(flight);
            if (flight.getFlightseats() <= 0) {
                return ResponseEntity.status(409).body(new TicketInfo(409,"No more seats available",null));
            }

            Ticket ticket = new Ticket();
            ticket.setFlightId(flight.getFlight_id());
            ticket.setUserId(user.getId());
            ticket.setJouneydate(flight.getDeparturetime());
            ticket.setTicket_price(flight.getFlightprice());
            String updateSeat=flight_url+flight_id.toString()+"?action=decrease";
            restTemplate.put(updateSeat,null);
            ticketRepository.save(ticket);
            TicketInfo ticketInfo =new TicketInfo(200,"Ticket for user created",null);
            return ResponseEntity.ok(ticketInfo);
    }

    public ResponseEntity<String> deleteTicket(String ticket_id) {
        Optional<Ticket> ticket = ticketRepository.findById(ticket_id);
        if(ticket.get().getJouneydate()==null){
            ticketRepository.deleteById(ticket_id);
            return ResponseEntity.ok("Ticket deleted successfully");
        }
        if(ticket.isPresent() && ticket.get().getJouneydate().isAfter(LocalDateTime.now())){
            Long flight_id = ticket.get().getFlightId();
            String updateSeat=flight_url+flight_id.toString()+"?action=increase";
            restTemplate.put(updateSeat,null);
            ticketRepository.deleteById(ticket_id);
            return ResponseEntity.ok("Ticket deleted successfully");
        }
        else{
            return ResponseEntity.status(409).body("Journey date exceeds current date");
        }
    }

    public ResponseEntity<TicketInfo> getTicket(String ticket_id) {
        Optional<Ticket> ticket= ticketRepository.findById(ticket_id);
        if(ticket.isPresent()){
            List<Ticket> tkt=new ArrayList<>();
            tkt.add(ticket.get());
            TicketInfo ticketInfo=new TicketInfo(200,"Ticket for user created",tkt);
            return ResponseEntity.ok(ticketInfo);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<TicketInfo> findAllTickets(Long user_id) {
        List<Ticket> tickets=ticketRepository.findByUserId(user_id);
        TicketInfo ticketInfo =new TicketInfo(200,"All tickets of user",tickets);
        return ResponseEntity.ok(ticketInfo);
    }

    public ResponseEntity<TicketInfo> findTicketByFlightId(Long flight_id, Long user_id) {
        List<Ticket> tickets=ticketRepository.findByUserIdAndFlightId(user_id, flight_id);
        TicketInfo ticketInfo =new TicketInfo(200,"Ticket for user created",tickets);
        return ResponseEntity.ok(ticketInfo);
    }
    public ResponseEntity<TicketInfo> findTicketByJourneyDate(Long user_id, LocalDateTime journey_date_start,LocalDateTime journey_date_end) {
        List<Ticket> tickets=ticketRepository.findByUserIdAndJouneydateBetween(user_id,journey_date_start,journey_date_end);
        TicketInfo ticketInfo =new TicketInfo(200,"Ticket for user created",tickets);
        return ResponseEntity.ok(ticketInfo);
    }

}
