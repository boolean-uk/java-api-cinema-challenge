package com.booleanuk.api.controller;

import com.booleanuk.api.model.Customer;
import com.booleanuk.api.model.DataStatus;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.CustomerRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import com.booleanuk.api.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable int id, @RequestBody Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }
    HashMap<String,Ticket> data = new HashMap<>();
    // @GetMapping("{customerId}/screenings/{screeningId}")
//    public ResponseEntity<HashMap<String, Ticket>> getTicketById(@PathVariable int customerId, @PathVariable int screeningId) {
//        data.clear();
//        int compare = 0;
//        int compare2 = 0;
//        boolean check = false;
//        Ticket ticket = null;
//        for (int i = 0; i<ticketRepository.findAll().size(); i++){
//            compare = ticketRepository.findAll().get(i).getCustomer().getId();
//            if (compare==customerId){
//                compare2 = ticketRepository.findAll().get(i).getScreening().getId();
//                if (compare2 == screeningId){
//                    ticket = ticketRepository.findAll().get(i);
//                    check = true;
//                }
//
//            }
//        }
//        if (check){
//            data.put("sucess",ticket);
//        }else {
//            data.put("failed",ticket);
//        }
////        Ticket customer = ticketRepository.findAll()
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
//
//        return ResponseEntity.ok(data);
//    }
    @GetMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<DataStatus> getTicketById(@PathVariable int customerId, @PathVariable int screeningId) {
        List<Ticket> tickets = ticketRepository.findByCustomerIdAndScreeningId(customerId, screeningId);
        if (tickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer and Screening not found");
        }

        List<Map<String, Object>> ticketDetailsList = tickets.stream().map(ticket -> {
            Map<String, Object> ticketDetails = new HashMap<>();
            ticketDetails.put("id", ticket.getId());
            ticketDetails.put("numSeats", ticket.getNumSeats());
            ticketDetails.put("createdAt", ticket.getCreatedAt());
            ticketDetails.put("updatedAt", ticket.getUpdatedAt());
            return ticketDetails;
        }).collect(Collectors.toList());

        DataStatus response = new DataStatus("success", ticketDetailsList);
        return ResponseEntity.ok(response);
    }
    @PostMapping("{customerId}/screenings/{screeningId}")
    public ResponseEntity<Ticket> createTicket(@PathVariable int customerId,
                                               @PathVariable int screeningId,
                                               @RequestBody Ticket request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));

        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        ticket.setNumSeats(request.getNumSeats());

        Ticket savedTicket = ticketRepository.save(ticket);

        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }
}