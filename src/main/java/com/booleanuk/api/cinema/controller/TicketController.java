package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

    /**
     * TESTING RESPONSE THINGY
     * @return
     */
    @GetMapping("/responsetest")
    public ResponseEntity<ApiResponse<Ticket>> getErrorOnPurpose() {

        ApiResponse<Ticket> response = new ApiResponse<>();
        response.setStatus("success");
        response.setData(response.asList(getAllTickets()));

        return ResponseEntity.ok(response);
    }
}
