package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private TicketRepository ticketRepository;
    private MovieRepository movieRepository;
    private ScreeningRepository screeningRepository;

    public TicketController(TicketRepository ticketRepository, MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.ticketRepository = ticketRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        return new ResponseEntity<>(new Response<>("success", ticketRepository.findAll()), HttpStatus.OK);
    }
}
