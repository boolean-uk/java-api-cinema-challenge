package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.entities.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepo;
import com.booleanuk.api.cinema.repositories.ScreeningRepo;
import com.booleanuk.api.cinema.repositories.TicketRepo;
import com.booleanuk.api.cinema.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ScreeningRepo screeningRepo;
    @Autowired
    TicketRepo ticketRepo;

    private Customer findCustomerById(int id) {
        return customerRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer with that id was found."));
    }

    private Screening findScreeningById(int id) {
        return screeningRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Screening with that id was found."));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(int id, Customer requestCustomer) {
        Customer customerFromDb = findCustomerById(id);
        if (requestCustomer.getName() != null) {
            customerFromDb.setName(requestCustomer.getName());
        }
        if (requestCustomer.getEmail() != null && Validation.isEmail(requestCustomer.getEmail())) {
            customerFromDb.setEmail(requestCustomer.getEmail());
        }
        if (requestCustomer.getPhone() != null) {
            customerFromDb.setPhone(requestCustomer.getPhone());
        }
        return customerRepo.save(customerFromDb);
    }

    @Override
    public Customer deleteCustomer(int id) {
        Customer customerFromDb = findCustomerById(id);
        customerRepo.delete(customerFromDb);
        return customerFromDb;
    }

    @Override
    public Ticket bookATicket(int customerId, int screeningId, Ticket ticket) {
        Customer customerFromDb = findCustomerById(customerId);
        Screening screeningFromDb = findScreeningById(screeningId);
        ticket.setCustomer(customerFromDb);
        ticket.setScreening(screeningFromDb);
        return ticketRepo.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets(int customerId, int screeningId) {
        return ticketRepo.findByCustomerIdAndScreeningId(customerId,screeningId);
    }
}
