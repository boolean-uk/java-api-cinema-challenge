package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.HelperUtils;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    /**
     * Logic: Use ApiResponse Class (and nested Message Class) to construct a JSON object,
     * that references a Generic Type 'T', which in this case is any of the Models.
     * The method checks if any instances of Customer exists on the server.
     * If false, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If true, instantiate an ApiResponse and wrap it around the body of a List of Customers.
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllCustomers() {
        try {
            List<Customer> customers = this.customerRepository.findAll();
            if (customers.isEmpty()) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                ApiResponse<Customer> okRequest = new ApiResponse<>("success", customers);
                return ResponseEntity.ok(okRequest);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    /**
     * Logic: Capture a snapshot of the instantiation time of Customer (since accurate precision of creation
     * is irrelevant, doing it at the top of the method body is ok). The method checks if any of the
     * payload criteria (i.e. malformed/illogical member variables) are missing.
     * If true, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If false, instantiate an ApiResponse and wrap it around the body of the single instance of Customer.
     * @param customer
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createCustomer(@RequestBody Customer customer) {
        try {
            Date date = new Date();
            customer.setCreatedAt(date);
            customer.setUpdatedAt(date);
            if (HelperUtils.invalidCustomerFields(customer)) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                Customer savedCustomer = this.customerRepository.save(customer);
                return HelperUtils.createdRequest(savedCustomer);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }

    }

    /**
     * Logic: Fetch the instance to delete by using local helper method, and verify the fetched value
     * is not null AND does not hold any tickets.
     * If true, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If false, instantiate an ApiResponse and wrap it around the body of the single instance of Customer.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCustomerById(@PathVariable int id) {
        try {
            Customer customerToDelete = getACustomer(id);
            if (customerToDelete == null || !customerToDelete.getTickets().isEmpty()) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                this.customerRepository.delete(customerToDelete);
                customerToDelete.setTickets(new ArrayList<>());
                ApiResponse<Customer> okRequest = new ApiResponse<>("success", customerToDelete);
                return ResponseEntity.status(HttpStatus.OK).body(okRequest);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    /**
     * Logic: Fetch the instance of Customer to update using the Pathvariable. Use static helper method
     * to verify if any of the payload criteria (i.e. malformed/illogical member variables) are missing.
     * Conduct the update if payload is correct.
     * @param id
     * @param customer
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateCustomerById(@PathVariable int id, @RequestBody Customer customer) {
        try {
            Customer customerToUpdate = getACustomer(id);
            if (HelperUtils.invalidCustomerFields(customer)) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                customerToUpdate.setName(customer.getName());
                customerToUpdate.setEmail(customer.getEmail());
                customerToUpdate.setPhone(customer.getPhone());
                customerToUpdate.setUpdatedAtToCurrentTimeInGMTPlus1();
                this.customerRepository.save(customerToUpdate);
                ApiResponse<Customer> createdRequest = new ApiResponse<>("success", customerToUpdate);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    /**
     * This should go to TicketController Class somehow.
     * Problem is that the Mapping from customers is occupied by this Controller.
     * Same for method two below.
     * @param customerId
     * @param ticket
     * @param screeningId
     * @return
     */
    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<?>> createTicket(@PathVariable int customerId, @RequestBody Ticket ticket, @PathVariable int screeningId) {
        try {
            Date date = new Date();
            ticket.setCreatedAt(date);
            ticket.setUpdatedAt(date);
            if (getACustomer(customerId) == null || getAScreening(screeningId) == null) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                Customer tempCustomer = getACustomer(customerId);
                Screening tempScreening = getAScreening(screeningId);
                ticket.setCustomer(tempCustomer);
                ticket.setScreening(tempScreening);
                ticket.setNumSeats(ticket.getNumSeats());
                Ticket savedTicket = this.ticketRepository.save(ticket);
                ApiResponse<Ticket> createdRequest = new ApiResponse<>("success", savedTicket);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<ApiResponse<?>> getAllTicketsByCustomerId(@PathVariable int customerId, @PathVariable int screeningId) {
        try {
            List<Ticket> tempTicketList = new ArrayList<>();
            for(Ticket ticket : ticketRepository.findAll()) {
                if (ticket.getCustomer().getId() == customerId && ticket.getScreening().getId() == screeningId) {
                    tempTicketList.add(ticket);
                }
            }
            if (tempTicketList.isEmpty()) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                ApiResponse<List<Ticket>> okRequest = new ApiResponse<>("success", tempTicketList);
                return ResponseEntity.status(HttpStatus.OK).body(okRequest);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
        }
    }


    /**
     * Local Helper method(s)
     * @param id
     * @return
     */
    private Customer getACustomer(int id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    private Screening getAScreening(int id) {
        return this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }
}
