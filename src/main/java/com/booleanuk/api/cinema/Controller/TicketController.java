package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Ticket;
import com.booleanuk.api.cinema.Repository.TicketRepository;
import com.booleanuk.api.cinema.ResponseWrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ResponseWrapper<Object>> create(@RequestBody Ticket newTicket) {
        try {
            Ticket savedTicket = this.ticketRepository.save(newTicket);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", savedTicket));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not create ticket, please check all required fields are correct."));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseWrapper getAll() {
        return new ResponseWrapper<>("success", this.ticketRepository.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<ResponseWrapper<Object>> update(@PathVariable("id") Integer id, @RequestBody Ticket updatedTicket) {
        Optional<Ticket> existingTicketOptional = this.ticketRepository.findById(id);

        if (existingTicketOptional.isPresent()) {
            try {
                Ticket existingTicket = existingTicketOptional.get();
                existingTicket.setCustomerId(updatedTicket.getCustomerId());
                existingTicket.setScreeningId(updatedTicket.getScreeningId());
                existingTicket.setNumSeats(updatedTicket.getNumSeats());
                Ticket savedTicket = this.ticketRepository.save(existingTicket);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", savedTicket));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not update ticket, please check all fields are correct."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("error", "No ticket with that id found."));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseWrapper<Object>> delete(@PathVariable("id") Integer id) {
        if (this.ticketRepository.existsById(id)) {
            this.ticketRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseWrapper<>("success", "Ticket deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("error", "No ticket with that ID was found."));
        }
    }
}