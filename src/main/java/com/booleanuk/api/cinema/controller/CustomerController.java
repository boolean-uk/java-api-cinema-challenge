package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.model.TicketNumber;
import com.booleanuk.api.cinema.view.CustomerRepository;
import com.booleanuk.api.cinema.view.ScreeningRepository;
import com.booleanuk.api.cinema.view.TicketRepository;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
	private CustomerRepository customerRepo;
	private TicketRepository ticketRepo;
	private ScreeningRepository screeningRepo;

	public CustomerController(CustomerRepository customerRepository, TicketRepository ticketRepository, ScreeningRepository screeningRepo){
		this.customerRepo = customerRepository;
		this.ticketRepo = ticketRepository;
		this.screeningRepo = screeningRepo;
	}

	@GetMapping //all
	public ResponseEntity<List<Customer>> getAll(){
		return new ResponseEntity<>(customerRepo.findAll(), HttpStatus.OK);
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

		return new ResponseEntity<>(customerRepo.save(customer), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Customer> putOne(@PathVariable int id, @RequestBody Customer customer){
		Customer customerToUpdate = customerRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		checkIfValidCustomer(customer);

		// update
		customerToUpdate.setName(customer.getName());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setPhone(customer.getPhone());

		// save and return
		return new ResponseEntity<>(customerRepo.save(customerToUpdate), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Customer> deleteMovie(@PathVariable int id){
		Customer delCustomer = customerRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		customerRepo.delete(delCustomer);
		return new ResponseEntity<>(delCustomer, HttpStatus.OK);
	}

	// extention
	@GetMapping("{customer}/screenings/{screening}")
	public ResponseEntity<List<Ticket>> getTickets(@PathVariable int customer, @PathVariable int screening){
		Customer c = customerRepo.findById(customer)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid customer"));

		return new ResponseEntity<>(c.getTickets(), HttpStatus.OK);
	}


	@PostMapping("{customer}/screenings/{screening}")
	public ResponseEntity<Ticket> postTicket(@PathVariable int customer, @PathVariable int screening, @RequestBody TicketNumber numSeats){
		Customer c = customerRepo.findById(customer)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid customer"));


		// check if valid screening
		Screening s = screeningRepo.findById(screening)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid screening"));

		Ticket t = new Ticket();
		t.setCustomer(c);
		t.setScreening(s);
		t.createdNow();
		t.setNumSeats(numSeats.tickets());

		ticketRepo.save(t);

		return new ResponseEntity<>(t, HttpStatus.CREATED);


	}
}
