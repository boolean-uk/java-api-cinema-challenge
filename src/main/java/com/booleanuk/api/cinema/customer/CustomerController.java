package com.booleanuk.api.cinema.customer;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("customers")
public class CustomerController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

//    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
//        List<Customer> customers = this.customerRepository.findAll();
//        List<CustomerDTO> customerDTOs = customers.stream()
//                .map(customerService::convertToDTO)
//                .collect(Collectors.toList());
//
//        ApiResponse<List<CustomerDTO>> res = new ApiResponse<>("success", customerDTOs);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<CustomerListResponse>getAllCustomers(){
        CustomerListResponse customerListResponse = new CustomerListResponse();
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customerService::convertToDTO)
                .toList();
        customerListResponse.set(customerDTOs);
        return new ResponseEntity<>(customerListResponse, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createCustomer(@RequestBody Customer customer) {
        if (isInvalidRequest(customer)) {
            return badRequest();
        }
        Customer createdCustomer = this.customerRepository.save(customer);
        createdCustomer.setTickets(new ArrayList<>());
        CustomerResponse response = new CustomerResponse();
        response.set(createdCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCustomerById(@PathVariable int id) {
        Customer customerToDelete = this.getACustomer(id);
        if (customerToDelete == null){
            return this.notFound();
        }
        this.customerRepository.delete(customerToDelete);
        CustomerResponse response = new CustomerResponse();
        response.set(customerToDelete);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateCustomerById(@PathVariable int id, @RequestBody Customer customer){

        if (isInvalidRequest(customer)) {
            return badRequest();
        }
        Customer customerToUpdate = getACustomer(id);
        if (customerToUpdate == null){
            return this.notFound();
        }

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        this.customerRepository.save(customerToUpdate);

        CustomerResponse response = new CustomerResponse();
        response.set(customerToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Customer getACustomer(int id){
        return this.customerRepository.findById(id).orElse(null);
    }

    private boolean isInvalidRequest(Customer customer){
        return customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null;
    }

    private ResponseEntity<ApiResponse<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create customer, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No customer with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
