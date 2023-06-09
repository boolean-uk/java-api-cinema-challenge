package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.ApiResponse.ApiResponse;
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
    private CustomerRepository customerRepository;

    @GetMapping
    public ApiResponse<List<Customer>> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        return new ApiResponse<>("success", customers);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(new ApiResponse<>("success", this.customerRepository.save(customer)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable int id) {
        Customer customer = null;
        customer = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers matching that id were found"));
        ApiResponse<Customer> response = new ApiResponse<>("success", customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers matching that id were found"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        return new ResponseEntity<>(new ApiResponse<>("success", this.customerRepository.save(customerToUpdate)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> deleteCustomer(@PathVariable int id) {
        Customer customerToDelete = this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers matching that id were found"));
        this.customerRepository.delete(customerToDelete);
        ApiResponse<Customer> response = new ApiResponse<>("success", customerToDelete);
        return ResponseEntity.ok(response);
    }
}
