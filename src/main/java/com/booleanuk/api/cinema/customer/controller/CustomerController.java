package com.booleanuk.api.cinema.customer.controller;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.customer.model.CustomerResponseDTO;
import com.booleanuk.api.cinema.customer.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.ResponseFactory;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.screening.repository.ScreeningRepository;
import com.booleanuk.api.cinema.ticket.model.Ticket;
import com.booleanuk.api.cinema.ticket.repository.TicketRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.booleanuk.api.cinema.response.ResponseFactory.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    // Workaround for exception 415
    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Response> addCustomer(@Valid @RequestBody Customer customer, BindingResult result) {

        if (result.hasErrors()) {
            return badRequestErrorResponse();
        }

        Customer savedCustomer = this.customerRepository.save(customer);
        CustomerResponseDTO response = convertToCustomerResponseDTO(savedCustomer);
        return createdSuccessResponse(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllCustomers() {
        List<CustomerResponseDTO> response = new ArrayList<>();
        this.customerRepository.findAll().forEach(customer ->
            response.add(convertToCustomerResponseDTO(customer))
        );
        return okSuccessResponse(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCustomerById(@PathVariable (name = "id") int id) {
        return this.customerRepository.findById(id).
                map(customer -> {
                    CustomerResponseDTO response = convertToCustomerResponseDTO(customer);
                    return okSuccessResponse(response);
                })
                .orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCustomer(@PathVariable (name = "id") int id, @Valid @RequestBody Customer updatedCustomer, BindingResult result) {

        if (result.hasErrors()) {
            return badRequestErrorResponse();
        }

        return this.customerRepository.findById(id).map(customerToUpdate -> {
            updateCustomerDetails(customerToUpdate, updatedCustomer);
            Customer savedCustomer = this.customerRepository.save(customerToUpdate);
            CustomerResponseDTO response = convertToCustomerResponseDTO(savedCustomer);
            return createdSuccessResponse(response);
        }).orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable (name = "id") int id){
        return this.customerRepository.findById(id).map(customerToDelete -> {
            this.customerRepository.delete(customerToDelete);
            return okSuccessResponse(customerToDelete);
        }).orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    /* Tickets */
    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> bookTicket(@PathVariable (name = "customerId") int customerId,
                                               @PathVariable (name = "screeningId") int screeningId,
                                               @Valid @RequestBody Ticket ticket, BindingResult result) {

        if (result.hasErrors()) {
            return badRequestErrorResponse();
        }

        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()){
            return notFoundErrorResponse();
        }

        Optional<Screening> optionalScreening = this.screeningRepository.findById(screeningId);
        if (optionalScreening.isEmpty()){
            return notFoundErrorResponse();
        }

        Customer customer = optionalCustomer.get();
        Screening screening = optionalScreening.get();

        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        Ticket savedTicket = this.ticketRepository.save(ticket);
        return createdSuccessResponse(savedTicket);

    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Response> getAllTickets(@PathVariable (name = "customerId") int customerId,
                                                  @PathVariable (name = "screeningId") int screeningId) {

        if (this.customerRepository.findById(customerId).isEmpty()) {
            return notFoundErrorResponse();
        }

        if (this.screeningRepository.findById(screeningId).isEmpty()) {
            return notFoundErrorResponse();
        }

        Customer customer = this.customerRepository.findById(customerId).get();
        Screening screening = this.screeningRepository.findById(screeningId).get();

        List<Ticket> ticketList = this.ticketRepository.findAllByCustomerAndScreening(customer, screening);

        return okSuccessResponse(ticketList);
    }


    private void updateCustomerDetails(Customer oldCustomer, Customer newCustomer) {
        oldCustomer.setName(newCustomer.getName());
        oldCustomer.setPhone(newCustomer.getPhone());
        oldCustomer.setEmail(newCustomer.getEmail());
        oldCustomer.setUpdatedAt(OffsetDateTime.now());
    }

    private CustomerResponseDTO convertToCustomerResponseDTO(Customer customer){
        return new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getCreatedAt(), customer.getUpdatedAt());
    }


}
