package com.booleanuk.api.cinema.customer.controller;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.customer.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.ResponseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.booleanuk.api.cinema.response.ResponseFactory.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<ResponseInterface> addCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = this.customerRepository.save(customer);
            return CreatedSuccessResponse(newCustomer);

        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @GetMapping
    public ResponseEntity<ResponseInterface> getAllCustomers() {
        return OkSuccessResponse(this.customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInterface> getCustomerById(@PathVariable (name = "id") int id) {
        var customer = findCustomerById(id);
        if (customer.isEmpty()) {
            return NotFoundErrorResponse();
        }
        return OkSuccessResponse(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInterface> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {

        if (findCustomerById(id).isEmpty()) {
            return NotFoundErrorResponse();
        }

        try {
            Customer customerToUpdate = findCustomerById(id).get();
            updateCustomer(customerToUpdate, customer);
            Customer updatedCustomer = this.customerRepository.save(customerToUpdate);

            return CreatedSuccessResponse(updatedCustomer);

        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInterface> deleteCustomer(@PathVariable (name = "id") int id){
        if (findCustomerById(id).isEmpty()) {
            return NotFoundErrorResponse();
        }

        try {
            Customer customerToDelete = findCustomerById(id).get();
            this.customerRepository.delete(customerToDelete);
            return OkSuccessResponse(customerToDelete);
        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    /* Helper functions */
    private Optional<Customer> findCustomerById(int id) {
        return this.customerRepository.findById(id);
    }

    private void updateCustomer(Customer oldCustomer, Customer newCustomer) {
        oldCustomer.setName(newCustomer.getName());
        oldCustomer.setPhone(newCustomer.getPhone());
        oldCustomer.setEmail(newCustomer.getEmail());
        oldCustomer.setUpdatedAt(OffsetDateTime.now());
    }
}
