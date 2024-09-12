package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.cinema.customers.tickets.Ticket;
import com.booleanuk.api.cinema.customers.tickets.TicketRepository;
import com.booleanuk.api.cinema.movies.screenings.ScreeningRepository;
import com.booleanuk.api.generic.GenericController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController extends GenericController<Customer> {
  private final TicketRepository ticketRepository;
  private final ScreeningRepository screeningRepository;

  public CustomerController(CustomerRepository repository, TicketRepository ticketRepository, ScreeningRepository screeningRepository) {
    super(repository);
    this.ticketRepository = ticketRepository;
    this.screeningRepository = screeningRepository;
  }

  @PostMapping(value = "{customerId}/screenings/{screeningId}")
  public ResponseEntity<Ticket> postTicket(@PathVariable int customerId, @PathVariable int screeningId, @RequestBody Ticket ticket) throws ResponseStatusException {
    var customer = this.repository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    var screening = this.screeningRepository.findById(screeningId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    ticket.setCustomer(customer);
    ticket.setScreening(screening);
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.ticketRepository.save(ticket));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping(value = "{customerId}/screenings/{screeningId}")
  public ResponseEntity<List<Ticket>> getTicket(@PathVariable int customerId, @PathVariable int screeningId) throws ResponseStatusException {
    this.repository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    this.screeningRepository.findById(screeningId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    try {
      return ResponseEntity.ok(this.ticketRepository.findAll().stream()
          .filter(ticket -> ticket.getCustomer().getId() == customerId && ticket.getScreening().getId() == screeningId)
          .toList()
      );
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}