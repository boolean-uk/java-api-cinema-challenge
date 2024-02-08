package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.cinema.customers.dto.CustomerDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOne(@PathVariable int id) {
        Customer customer = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addOne(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(
                modelMapper.map(this.customerRepository.save(customer), CustomerDTO.class),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateOne(@PathVariable int id, @Valid @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that ID found"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<>(modelMapper.map(this.customerRepository.save(customerToUpdate), CustomerDTO.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> deleteOne(@PathVariable int id){
        Customer customer = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that ID found"));
        try{
            this.customerRepository.delete(customer);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This customer has active tickets");
        }
        return new ResponseEntity<>(modelMapper.map(customer, CustomerDTO.class), HttpStatus.OK);

    }


}
