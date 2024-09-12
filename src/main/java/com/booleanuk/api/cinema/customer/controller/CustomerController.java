package com.booleanuk.api.cinema.customer.controller;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.customer.repository.CustomerRepository;
import com.booleanuk.api.cinema.response.ResponseInterface;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.screening.repository.ScreeningRepository;
import com.booleanuk.api.cinema.ticket.model.Ticket;
import com.booleanuk.api.cinema.ticket.repository.TicketRepository;
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

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ScreeningRepository screeningRepository;

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
        if (findCustomerById(id).isEmpty()) {
            return NotFoundErrorResponse();
        }
        return OkSuccessResponse(findCustomerById(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInterface> updateCustomer(@PathVariable (name = "id") int id, @RequestBody Customer customer) {

        if (findCustomerById(id).isEmpty()) {
            return NotFoundErrorResponse();
        }

        try {
            Customer customerToUpdate = findCustomerById(id).get();
            update(customerToUpdate, customer);
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

    /* Tickets */

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ResponseInterface> bookTicket(@PathVariable (name = "customerId") int customerId,
                                                        @PathVariable (name = "screeningId") int screeningId,
                                                        @RequestBody Ticket ticket) {

        if (this.customerRepository.findById(customerId).isEmpty() || this.screeningRepository.findById(screeningId).isEmpty()) {
            return NotFoundErrorResponse();
        }

        Customer customer = this.customerRepository.findById(customerId).get();
        Screening screening = this.screeningRepository.findById(screeningId).get();

        try {
            ticket.setCustomer(customer);
            ticket.setScreening(screening);
            return CreatedSuccessResponse(ticketRepository.save(ticket));
        } catch (Exception e) {
            return NotFoundErrorResponse();
        }
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ResponseInterface> getAllTickets(@PathVariable (name = "customerId") int customerId,
                                                           @PathVariable (name = "screeningId") int screeningId) {

        if (this.customerRepository.findById(customerId).isEmpty() || this.screeningRepository.findById(screeningId).isEmpty()) {
            return NotFoundErrorResponse();
        }

        Customer customer = this.customerRepository.findById(customerId).get();
        Screening screening = this.screeningRepository.findById(screeningId).get();

        return OkSuccessResponse(this.ticketRepository.findAllByCustomerAndScreening(customer, screening));
    }


    /* Helper functions */
    private Optional<Customer> findCustomerById(int id) {
        return this.customerRepository.findById(id);
    }

    private void update(Customer oldCustomer, Customer newCustomer) {
        oldCustomer.setName(newCustomer.getName());
        oldCustomer.setPhone(newCustomer.getPhone());
        oldCustomer.setEmail(newCustomer.getEmail());
        oldCustomer.setUpdatedAt(OffsetDateTime.now());
    }


}
