package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.ResponseWrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ResponseWrapper<Object>> create(@RequestBody Customer newCustomer) {
        try {

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            newCustomer.setCreatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));
            newCustomer.setUpdatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));
            Customer savedCustomer = this.customerRepository.save(newCustomer);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", savedCustomer.getId());
            response.put("name", savedCustomer.getName());
            response.put("email", savedCustomer.getEmail());
            response.put("phone", savedCustomer.getPhone());
            response.put("createdAt", currentDateTime.format(formatter));
            response.put("updatedAt", currentDateTime.format(formatter));

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not create customer, please check all required fields are correct."));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseWrapper getAll() {
        return new ResponseWrapper<>("success", this.customerRepository.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<ResponseWrapper<Object>> update(@PathVariable("id") Integer id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = this.customerRepository.findById(id);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        if (existingCustomerOptional.isPresent()) {
            try {
                Customer existingCustomer = existingCustomerOptional.get();
                existingCustomer.setName(updatedCustomer.getName());
                existingCustomer.setEmail(updatedCustomer.getEmail());
                existingCustomer.setPhone(updatedCustomer.getPhone());
                existingCustomer.setUpdatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));

                Customer savedCustomer = this.customerRepository.save(existingCustomer);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", savedCustomer));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not update customer, please check all fields are correct."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("error", "No customer with that id found."));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseWrapper<Object>> delete(@PathVariable("id") Integer id) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            Customer deletedCustomer = customerOptional.get();
            this.customerRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseWrapper<>("success", deletedCustomer));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("error", "No customer with that ID was found."));
        }
    }
}
