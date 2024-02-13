package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.*;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ScreeningRepository screeningRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping
	public CustomResponse<List<Customer>> getAll() {

		return new CustomResponse<>("success", customerRepository.findAll());
	}
	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSxxx");

	@PostMapping
	public ResponseEntity<CustomResponse<?>> create(@RequestBody CustomerDTO customerDTO) {
		Customer customer = new Customer(customerDTO.getName(),customerDTO.getEmail(),customerDTO.getPhone());
		if (customer.getName() == null || customer.getEmail() == null || customer.getPhone() == null) {
			CustomResponse<Object> errorResponse = new CustomResponse<>("error", Collections.singletonMap("message", "bad request"));
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		Customer newCustomer = new Customer(customer.getName(), customer.getEmail(), customer.getPhone());
		Customer savedCustomer = customerRepository.save(newCustomer);

		CustomResponse<CustomerDTO> response = new CustomResponse<>("success", toDTO(savedCustomer));
		return ResponseEntity.ok(response);
	}


	@PostMapping("{customer_id}/screenings/{screening_id}")
	public ResponseEntity<CustomResponse<?>> createTicket(@PathVariable int customer_id, @PathVariable int screening_id, @RequestBody TicketDTO ticketDTO) {
		Screening screening = screeningRepository.findById(screening_id).orElse(null);
		if(screening==null){
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		Customer customer = customerRepository.findById(customer_id).orElse(null);
		if(customer==null){
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		Ticket ticket = new Ticket(screening,customer, ticketDTO.getNumSeats());
		Ticket newTicket = new Ticket(screening, customer, ticket.getNumSeats());
		ticketRepository.save(newTicket);
		return new ResponseEntity<>(new CustomResponse<>("success",toDTO(newTicket)), HttpStatus.CREATED);
	}

	@GetMapping("{customer_id}/screenings/{screening_id}")
	public ResponseEntity<CustomResponse<?>> getTickets(@PathVariable int customer_id, @PathVariable int screening_id) {
		List<Ticket> tickets = ticketRepository.findByCustomer_IdAndScreening_Id(customer_id, screening_id);

		if (tickets.isEmpty()) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(new CustomResponse<>("success", tickets));
	}

	@GetMapping("{id}")
	public ResponseEntity<CustomResponse<?>> get(@PathVariable int id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new CustomResponse<>("success", customer));
	}

	@PutMapping("{id}")
	public ResponseEntity<CustomResponse<?>> update(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
		Customer customer = new Customer(customerDTO.getName(),customerDTO.getEmail(),customerDTO.getPhone());
		Customer newCustomer = customerRepository.findById(id).orElse(null);
		if (newCustomer == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);
		}
		newCustomer.setName(customer.getName());
		newCustomer.setEmail(customer.getEmail());
		newCustomer.setPhone(customer.getPhone());
		customerRepository.save(newCustomer);
		return new ResponseEntity<>(new CustomResponse<>("success",toDTO(newCustomer)), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<CustomResponse<?>> delete(@PathVariable int id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		customerRepository.delete(customer);
		return ResponseEntity.ok(new CustomResponse<>("success", toDTO(customer)));

	}
	private CustomerDTO toDTO(Customer customer) {
		return new CustomerDTO(
				customer.getId(),
				customer.getName(),
				customer.getEmail(),
				customer.getPhone(),
				customer.getCreatedAt().format(outputFormatter),
				customer.getUpdatedAt().format(outputFormatter),
				customer.getTickets()
		);
	}
	private TicketDTO toDTO(Ticket ticket){
		return new TicketDTO(
				ticket.getId(),
				ticket.getNumSeats(),
				ticket.getCreatedAt().format(outputFormatter),
				ticket.getUpdatedAt().format(outputFormatter)
		);
	}


}
