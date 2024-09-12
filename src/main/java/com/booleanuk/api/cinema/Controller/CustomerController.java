package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;


    @PostMapping
    public ResponseEntity<Customer> createAuthor(@RequestBody Customer customer){
        try {
            return new ResponseEntity<Customer>(this.repository.save(customer),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new customer, please check all required fields are correct.");
        }


    }

    @GetMapping
    public List<Customer> getAll() {
        return this.repository.findAll();
    }


    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id,
                                               @RequestBody Customer customer){
        Customer customerToUpdate=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No customer with that ID found")
        );
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customerToUpdate.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        try{
            return new ResponseEntity<Customer>(this.repository.save(customerToUpdate
            ), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update customer, please check all required fields are correct.");
        }




    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id){
        Customer customerToDelete=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No customer with that ID found")
        );

        this.repository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }
}
