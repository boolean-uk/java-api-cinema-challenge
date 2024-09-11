package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.view.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
	private CustomerRepository repo;

	public CustomerController(CustomerRepository customerRepository){
		this.repo = customerRepository;
	}

	@GetMapping //all
	public ResponseEntity<List<Customer>> getAll(){
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}


	public void checkIfValidCustomer(Customer customer){
		try{
			if (customer.getName() == null ||
					customer.getEmail() == null ||
					customer.getPhone() == null
			) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
			}
		} catch (Exception e) { // catches null exception and response and properly throws the correct
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
		}
	}

	@PostMapping
	public ResponseEntity<Customer> postOne(@RequestBody Customer customer){
		checkIfValidCustomer(customer);
		// throws error if invalid.

		customer.setCreatedAt(OffsetDateTime.now());

		return new ResponseEntity<>(repo.save(customer), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Customer> putOne(@PathVariable int id, @RequestBody Customer customer){
		Customer customerToUpdate = repo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		checkIfValidCustomer(customer);

		// update
		customerToUpdate.setName(customer.getName());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setPhone(customer.getPhone());

		// save and return
		return new ResponseEntity<>(repo.save(customerToUpdate), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Customer> deleteMovie(@PathVariable int id){
		Customer delCustomer = repo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		repo.delete(delCustomer);
		return new ResponseEntity<>(delCustomer, HttpStatus.OK);
	}

}
