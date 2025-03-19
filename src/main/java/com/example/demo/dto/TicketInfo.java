package com.example.demo.dto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TicketInfo {
    public Integer status;
    public String message;
    public List<Ticket> ticketsList;


    public TicketInfo( int status, String message, List<Ticket> ticketsList) {
        this.status = status;
        this.message = message;
        this.ticketsList = ticketsList;
    }
}
