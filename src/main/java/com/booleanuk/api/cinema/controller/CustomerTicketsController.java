package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.CustomerDTO;
import com.booleanuk.api.cinema.dto.TicketDTO;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.service.ICustomerService;
import com.booleanuk.api.cinema.service.ITicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerTicketsController {

    private final ICustomerService customerService;
    private final ITicketService ticketService;
    @Autowired
    public CustomerTicketsController(ICustomerService customerService, ITicketService ticketService) {
        this.customerService = customerService;
        this.ticketService = ticketService;
    }

    private List<CustomerDTO> convertToDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOS.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(),
                    customer.getPhone(), customer.getCreatedAt(), customer.getUpdatedAt()));
        }
        return customerDTOS;
    }

    private List<TicketDTO> convertTicketToDTOs(List<Ticket> tickets) {
        List <TicketDTO> ticketDTOS = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketDTOS.add(new TicketDTO(ticket.getId(), ticket.getNumSeats(), ticket.getCreatedAt(), ticket.getUpdatedAt()));
        }
        return ticketDTOS;
    }

    private CustomerDTO map (Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setCreatedAt(customer.getCreatedAt());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());
        return customerDTO;
    }

    private TicketDTO mapTicket (Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setNumSeats(ticket.getNumSeats());
        ticketDTO.setCreatedAt(ticket.getCreatedAt());
        ticketDTO.setUpdatedAt(ticket.getUpdatedAt());
        return ticketDTO;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers () {
            List<Customer> customers = customerService.getCustomers();
            List<CustomerDTO> customerDTOS = convertToDTOs(customers);
            return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer (@RequestBody @Valid CustomerDTO dto) {
        Customer customer = customerService.insertCustomer(dto);
        CustomerDTO customerDTO = map(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer (@RequestBody @Valid CustomerDTO dto, @PathVariable int id) {
        dto.setId(id);
        Customer customer = customerService.updateCustomer(dto);
        CustomerDTO customerDTO = map(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer (@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        customerService.deleteCustomer(id);
        CustomerDTO customerDTO = map(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable int customerId, @PathVariable int screeningId) {
        List<Ticket> tickets = ticketService.getTickets(customerId, screeningId);
        List<TicketDTO> ticketDTOS = convertTicketToDTOs(tickets);
        return new ResponseEntity<>(ticketDTOS, HttpStatus.OK);
    }

    @PostMapping("/{customerId}/screenings/{screeningId}")
    public ResponseEntity<TicketDTO> addTicket (@PathVariable int customerId, @PathVariable int screeningId, @RequestBody TicketDTO dto) {
        Ticket ticket = ticketService.insertTicket(customerId, screeningId, dto);
        TicketDTO ticketDTO = mapTicket(ticket);
        return new ResponseEntity<>(ticketDTO, HttpStatus.CREATED);
    }

}
