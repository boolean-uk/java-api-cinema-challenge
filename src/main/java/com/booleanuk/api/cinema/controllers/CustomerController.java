package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.DTOs.CustomerNoRelationsDTO;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private record CustomerSingleDTO (String status, CustomerNoRelationsDTO data) {}
    private record CustomerListDTO (String status, List<CustomerNoRelationsDTO> data) {}
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    // ------------------ ENDPOINTS ------------------//
    //region // POST //
    @PostMapping
    public ResponseEntity<CustomerSingleDTO> create(@RequestBody Customer customer) {
        Customer customerToCreate = customer;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CustomerSingleDTO("success", modelMapper
                        .map(this.customerRepository.save(customerToCreate), CustomerNoRelationsDTO.class)
                ));
    }
    //endregion
    //region // GET //
    @GetMapping
    public ResponseEntity<CustomerListDTO> getAll() {
        return ResponseEntity
                .ok(new CustomerListDTO("success", this.customerRepository.findAll().stream()
                        .map(x -> modelMapper
                                .map(x, CustomerNoRelationsDTO.class))
                        .collect(Collectors.toList())
                ));
    }
    //endregion
    //region // PUT //
    @PutMapping("/{id}")
    public ResponseEntity<CustomerSingleDTO> update(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer with this id doesn't exist."
                ));

        boolean hasChanged = false;
        if(!customerToUpdate.getName().equals(customer.getName())) {
            customerToUpdate.setName(customer.getName());
            hasChanged = true;
        }
        if(!customerToUpdate.getName().equals(customer.getName())) {
            customerToUpdate.setEmail(customer.getEmail());
            hasChanged = true;
        }
        if(!customerToUpdate.getName().equals(customer.getName())) {
            customerToUpdate.setPhone(customer.getPhone());
            hasChanged = true;
        }

        if (hasChanged)
            customerToUpdate.setUpdatedAt(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CustomerSingleDTO("success", modelMapper
                        .map(this.customerRepository.save(customerToUpdate), CustomerNoRelationsDTO.class)
                ));
    }
    //endregion
    //region // DELETE //
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerSingleDTO> delete(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Customer with this id doesn't exist."
                ));

        this.customerRepository.delete(customerToDelete);
        return ResponseEntity
                .ok(new CustomerSingleDTO("success", modelMapper
                .map(customerToDelete, CustomerNoRelationsDTO.class)
                ));
    }
    //endregion
}