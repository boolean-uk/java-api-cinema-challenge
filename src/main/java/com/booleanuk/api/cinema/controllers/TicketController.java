package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.tickets.TicketResponseDto;
import com.booleanuk.api.cinema.Dtos.tickets.TicketResponseSingleDto;
import com.booleanuk.api.cinema.entities.Ticket;
import com.booleanuk.api.cinema.services.ticket.TicketServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {

    @Autowired
    TicketServiceInterface ticketService;

    @GetMapping
    public TicketResponseDto getAllTicketsOfaCustomerOfAScreening(@PathVariable Integer customerId, @PathVariable Integer screeningId) {
        return new TicketResponseDto("success", ticketService.findByCustomerIdAndScreeningId(customerId, screeningId));
    }

    @PostMapping
    public TicketResponseSingleDto CreateTicketOfaCustomerOfAScreening(@PathVariable Integer customerId, @PathVariable Integer screeningId, @RequestBody Ticket ticket) {
        return new TicketResponseSingleDto("success", ticketService.createTicket(customerId, screeningId, ticket));
    }
}
