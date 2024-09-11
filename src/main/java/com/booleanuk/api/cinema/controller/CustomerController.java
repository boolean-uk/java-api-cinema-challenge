package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.*;
import com.booleanuk.api.cinema.view.CustomerRepository;
import com.booleanuk.api.cinema.view.ScreeningRepository;
import com.booleanuk.api.cinema.view.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers")
public class CustomerController {
	private final CustomerRepository customerRepo;
	private final TicketRepository ticketRepo;
	private final ScreeningRepository screeningRepo;

	public CustomerController(CustomerRepository customerRepository, TicketRepository ticketRepository, ScreeningRepository screeningRepo){
		this.customerRepo = customerRepository;
		this.ticketRepo = ticketRepository;
		this.screeningRepo = screeningRepo;
	}

	@GetMapping //all
	public ResponseEntity<ResponseObject<List<Customer>>> getAll(){
		return new ResponseEntity<>(new ResponseObject<>("Success", customerRepo.findAll()), HttpStatus.OK);
	}


	public void checkIfValidCustomer(Customer customer){
		try{
			if (customer.getName() == null ||
					customer.getEmail() == null ||
					customer.getPhone() == null
			) {
				throw new Exception("Parse error found");
			}
		} catch (Exception e) { // catches null exception and response and properly throws the correct
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
		}
	}

	public ResponseEntity<ResponseObject<Customer>> customerNotFound(){
		return new ResponseEntity<>(new ResponseObject<>("Failed"), HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<ResponseObject<Customer>> postOne(@RequestBody Customer customer){
		try {
			checkIfValidCustomer(customer);
			// throws error if invalid.
		} catch (ResponseStatusException e) {
			return customerNotFound();
		}
		customer.createdNow();
		return new ResponseEntity<>(new ResponseObject<>("Success",customerRepo.save(customer)), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseObject<Customer>> putOne(@PathVariable int id, @RequestBody Customer customer){
		Customer customerToUpdate;
		try {
			customerToUpdate = customerRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

			checkIfValidCustomer(customer);
		} catch (ResponseStatusException e) {
			return customerNotFound();
		}

		// update
		customerToUpdate.setName(customer.getName());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setPhone(customer.getPhone());
		customerToUpdate.updatedNow();

		// save and return
		return new ResponseEntity<>(new ResponseObject<>("Success", customerRepo.save(customerToUpdate)), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<Customer>> deleteMovie(@PathVariable int id){
		Customer delCustomer;
		try{
			delCustomer = customerRepo.findById(id)
					.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		} catch (ResponseStatusException e) {
			return customerNotFound();
		}

		customerRepo.delete(delCustomer);
		return new ResponseEntity<>(new ResponseObject<>("Success", delCustomer), HttpStatus.OK);
	}

	// extention
	@GetMapping("{customer}/screenings/{screening}")
	public ResponseEntity<ResponseObject<List<Ticket>>> getTickets(@PathVariable int customer, @PathVariable int screening){
		Customer c;
		try {
			c = customerRepo.findById(customer)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid customer"));
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed"), HttpStatus.NOT_FOUND);
		}

		Screening s;
		try{
			s = screeningRepo.findById(screening)
					.orElseThrow(() -> new Exception("Failed to find screening"));
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed"), HttpStatus.NOT_FOUND);
		}

		List<Ticket> tickets = c.getTickets();
		tickets = tickets.stream()
				.filter(ticket -> ticket.getScreening() == s)
				.toList();


		return new ResponseEntity<>(new ResponseObject<>("Success", tickets), HttpStatus.OK);
	}


	@PostMapping("{customer}/screenings/{screening}")
	public ResponseEntity<ResponseObject<Ticket>> postTicket(@PathVariable int customer, @PathVariable int screening, @Validated @RequestBody TicketNumber numSeats){
		Customer c;
		Screening s;
		try {
			c = customerRepo.findById(customer)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid customer"));

			// check if valid screening
			s = screeningRepo.findById(screening)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid screening"));

		} catch (Exception e){
			return new ResponseEntity<>(new ResponseObject<>("Failed"), HttpStatus.NOT_FOUND);

		}

		// update ticket
		Ticket t = new Ticket();
		t.setCustomer(c);
		t.setScreening(s);
		t.createdNow();
		System.out.println(numSeats.getNumSeats());
		t.setNumSeats(numSeats.getNumSeats());

		// save and return response object
		ticketRepo.save(t);
		return new ResponseEntity<>(new ResponseObject<>("Success", t), HttpStatus.CREATED);


	}
}
