package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.CustomResponse;
import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository repo;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Customer>>> getAll() {
        return new ResponseEntity<>(
                new CustomResponse<>(repo.findAll()),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse<Customer>> getOne(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(
                new CustomResponse<>(
                        repo.findById(id).orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "No customers with that id were found"
                            )
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Customer>> createOne(@RequestBody Customer customer) {
        return new ResponseEntity<>(
                new CustomResponse<>(repo.save(customer)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse<Customer>> updateOne(@PathVariable(name = "id") int id, @RequestBody Customer customer) {
        Customer toUpdate = repo.findById(id)
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "No customers with that id were found"
                                    )
                            );

        toUpdate.setName(customer.getName());
        toUpdate.setEmail(customer.getEmail());
        toUpdate.setPhone(customer.getPhone());

        return new ResponseEntity<>(
                new CustomResponse<>(repo.save(toUpdate)),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse<Customer>> deleteOne(@PathVariable(name = "id") int id) {
        Customer deleted = repo.findById(id)
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "No customers with that id were found"
                                    )
                            );

        repo.deleteById(id);

        return new ResponseEntity<>(
                new CustomResponse<>(deleted),
                HttpStatus.OK
        );
    }
}
