package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	ScreeningRepository screeningRepository;
	@Autowired
	CustomerRepository customerRepository;

	@GetMapping
	public List<Ticket> getAll() {
		return ticketRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
		Screening screening = screeningRepository.findById(ticket.getScreening().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		Customer customer = customerRepository.findById(ticket.getCustomer().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		Ticket newTicket = new Ticket(screening, customer, ticket.getNumSeats());
		return new ResponseEntity<>(ticketRepository.save(newTicket), HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<Ticket> get(@PathVariable int id) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		return ResponseEntity.ok(ticket);
	}

	@PutMapping("{id}")
	public ResponseEntity<Ticket> update(@PathVariable int id, @RequestBody Ticket ticket) {
		Ticket newTicket = ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		newTicket.setScreening(ticket.getScreening());
		newTicket.setCustomer(ticket.getCustomer());
		newTicket.setNumSeats(ticket.getNumSeats());
		return new ResponseEntity<>(ticketRepository.save(newTicket), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Ticket> delete(@PathVariable int id) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		ticketRepository.delete(ticket);
		return ResponseEntity.ok(ticket);

	}

}
