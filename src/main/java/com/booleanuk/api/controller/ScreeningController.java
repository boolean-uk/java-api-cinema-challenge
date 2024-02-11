package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.CustomerRepository;
import com.booleanuk.api.repository.MovieRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import com.booleanuk.api.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getById(@PathVariable int id){
        Screening employee = this.screeningRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);

    }
    @GetMapping
    public List<Screening> getAll(){
        return this.screeningRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Screening> create(@RequestBody Screening ticket){
        //if you have many to one relation in employee 'class for department, then
        //you need to do it like this, by making a temp department.
        Movie tempMovie = movieRepository.findById(ticket.getMovie()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        ticket.setMovie(tempMovie);


        return ResponseEntity.ok(screeningRepository.save(ticket));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Screening> delete(@PathVariable int id){
//        Screening delete = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
//        this.screeningRepository.delete(delete);
//        return ResponseEntity.ok(delete);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> delete(@PathVariable int id) {
        Screening toDelete = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        this.screeningRepository.delete(toDelete);
        toDelete.setTickets(new ArrayList<Ticket>());
        return ResponseEntity.ok(toDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateEmployee(@PathVariable int id, @RequestBody Screening book){
        Screening update = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        Movie tempMovie = movieRepository.findById(book.getMovie()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        update.setMovie(tempMovie);
        update.setCreatedAt(book.getCreatedAt());
        update.setUpdatedAt(book.getUpdatedAt());
        return new ResponseEntity<>(this.screeningRepository.save(update), HttpStatus.CREATED);
    }
}
