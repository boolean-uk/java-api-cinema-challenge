package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Response;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    // @Autowired Prefer constructor injection to field injection. Done automatically by Spring
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@RequestBody Customer customer) {
        if (isValidObject(customer)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.success(this.customerRepository.save(customer)));
        }
        return ResponseEntity.badRequest().body(Response.BAD_REQUEST);
    }

    @GetMapping
    public Response<List<Customer>> getAll() {
        return Response.success(this.customerRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable int id) {
        if (this.customerRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }
        return ResponseEntity.ok(Response.success(this.customerRepository.findById(id).get()));
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Customer customer) {
        if (!isValidObject(customer)) {
            return ResponseEntity.badRequest().body(Response.BAD_REQUEST);
        }
        if (this.customerRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Customer customerToUpdate = this.customerRepository.findById(id).get();
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success(this.customerRepository.save(customerToUpdate)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteById(@PathVariable int id) {
        if (this.customerRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Customer customerToDelete = this.customerRepository.findById(id).get();
        this.customerRepository.delete(customerToDelete);
        return ResponseEntity.ok(Response.success(customerToDelete));
    }

    private boolean isValidObject(Customer customer) {
        if (Stream.of(customer.getName(), customer.getEmail(), customer.getPhone())
                .anyMatch(field -> field == null || field.isBlank())) {
            return false;
        }
        // TODO Validate email, phone, etc
        return true;
    }
}
