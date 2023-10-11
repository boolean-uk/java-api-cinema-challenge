package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.DTOs.TicketNoRelationsDTO;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    private record TicketSingleDTO (String status, TicketNoRelationsDTO data) {}
    private record TicketListDTO (String status, List<TicketNoRelationsDTO> data) {}
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    // ------------------ ENDPOINTS ------------------//
    //region // POST //
    @PostMapping
    public ResponseEntity<TicketSingleDTO> create(@RequestBody Ticket ticket, @PathVariable int customerId, @PathVariable int screeningId) {
        Ticket ticketToCreate = ticket;
        ticketToCreate.setCustomer(new Customer(customerId));
        ticketToCreate.setScreening(new Screening(screeningId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new TicketSingleDTO("success", modelMapper
                        .map(this.ticketRepository.save(ticketToCreate), TicketNoRelationsDTO.class)
                ));
    }
    //endregion
    //region // GET //
    @GetMapping
    public ResponseEntity<TicketListDTO> getAll(@PathVariable int customerId, @PathVariable int screeningId) {
        return ResponseEntity
                .ok(new TicketListDTO("success", this.ticketRepository.findAll().stream()
                        .filter(x -> x.getCustomer().getId() == customerId && x.getScreening().getId() == screeningId)
                        .map(x -> modelMapper
                                .map(x, TicketNoRelationsDTO.class))
                        .collect(Collectors.toList())
                ));
    }
    //endregion
}