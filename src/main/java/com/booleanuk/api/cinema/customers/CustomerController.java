package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.cinema.customers.dto.CustomerDTO;
import com.booleanuk.api.helpers.ResponseHandler;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public ResponseEntity<Object> getAll() {
       return ResponseHandler.generateResponse(HttpStatus.OK, this.customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable int id)  {
        try{
            Customer customer = this.customerRepository
                    .findById(id)
                    .orElseThrow(Exception::new);
            return ResponseHandler.generateResponse(HttpStatus.OK, customer);
        }
        catch (Exception e){
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        }



    }

    @PostMapping
    public ResponseEntity<Object>  addOne(@Valid @RequestBody Customer customer) {
        return ResponseHandler.generateResponse(HttpStatus.CREATED,
                modelMapper.map(this.customerRepository.save(customer), CustomerDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object>  updateOne(@PathVariable int id, @Valid @RequestBody Customer customer)  {
        try{
            Customer customerToUpdate = this.customerRepository
                    .findById(id)
                    .orElseThrow(Exception::new);

            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
            customerToUpdate.setUpdatedAt(LocalDateTime.now());

            return ResponseHandler.generateResponse(HttpStatus.CREATED, modelMapper.map(this.customerRepository.save(customerToUpdate), CustomerDTO.class));
        }
        catch (Exception e){
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable int id){

        try{
            Customer customer = this.customerRepository
                    .findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            this.customerRepository.delete(customer);
            return new ResponseEntity<>(modelMapper.map(customer, CustomerDTO.class), HttpStatus.OK);
        }
        catch (ChangeSetPersister.NotFoundException e){
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return ResponseHandler.generateError(HttpStatus.BAD_REQUEST);
        }


    }


}
