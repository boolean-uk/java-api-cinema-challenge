package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//this is extra, in case i want to see all available tickets
@RestController
@RequestMapping("tickets")
public class TicketController {
    @Autowired
    private TicketRepository repository;

    //in case i want to see all tickets
    //does not work as expected but i don't mind since it isn't required
    @GetMapping
    public List<Ticket> getAll(){
        return this.repository.findAll();
    }
}
