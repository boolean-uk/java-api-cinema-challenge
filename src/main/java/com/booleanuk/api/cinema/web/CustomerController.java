package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.CreateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateCustomerRequestDTO;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // TODO: MODIFY THE SPECS TO INCLUDE NEW ENDPOINTS
    @GetMapping
    public ResponseEntity<Response<List<CustomerResponseDTO>>> getAllCustomers() {
        List<CustomerResponseDTO> customerResponseDTOs = customerService.getAllCustomers();
        Response<List<CustomerResponseDTO>> response = new Response<>();
        response.set(customerResponseDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCustomerById(@PathVariable Long id) {
        try {
            CustomerResponseDTO customer = customerService.getCustomerById(id);
            return getResponseEntity(customer);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE);
            // TODO: maybe return the error message instead of the constant
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCustomer(@RequestBody @Valid CreateCustomerRequestDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CustomerResponseDTO createdCustomer = this.customerService.createCustomer(customerDTO);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(createdCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCustomer(@PathVariable Long id, @RequestBody @Valid UpdateCustomerRequestDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            CustomerResponseDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
            return getResponseEntity(updatedCustomer);
        } catch (ResponseStatusException e) {
            // HERE
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }


    }
// TODO: Check for other hardcoded values and put them in constants, eg by errors;
    // TODO: Extract this in a helper class with generics?
    private ResponseEntity<Response<?>> getResponseEntity(CustomerResponseDTO customer) {

        if (customer == null) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.set(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCustomer(@PathVariable Long id) {
        try {
            CustomerResponseDTO deletedCustomer = customerService.deleteCustomer(id);
            return getResponseEntity(deletedCustomer);
        } catch (ResourceNotFoundException e) {
            // TODO: Make errors consistent in the serviceImpl
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}