package com.booleanuk.api.cinema.customers;


import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
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
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCustomers() {
        return new ResponseEntity<>(new Response<>(customerRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody Customer customer) {
        if (customer == null) {
            ErrorResponse errorResponse = new ErrorResponse("Failed to create customer");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
        }
        customer.setDate_created(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(new Response<>(this.customerRepository.save(customer)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "id") int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such customer."));
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setDate_updated(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") int id) {
        Customer toDelete = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such customer found"));
        this.customerRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);

    }

}
