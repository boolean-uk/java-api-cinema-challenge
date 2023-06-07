package com.booleanuk.api.cinema.services.customer;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.entities.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepo;
import com.booleanuk.api.cinema.repositories.ScreeningRepo;
import com.booleanuk.api.cinema.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ICustomerService implements CustomerServiceInterface {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    ScreeningRepo screeningRepo;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    private Customer findCustomerById(int id) {
        return customerRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer with that id was found"));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public Customer updateCustomer(int id, Customer customer) {
        Customer customerToBeUpdated = findCustomerById(id);
        if (customer.getName() != null) {
            customerToBeUpdated.setName(customer.getName());
        }
        if (customer.getEmail() != null) {
            customerToBeUpdated.setEmail(customer.getEmail());
        }
        if (customer.getPhone() != null) {
            customerToBeUpdated.setPhone(customer.getPhone());
        }

        return customerRepo.save(customerToBeUpdated);
    }

    @Override
    public Customer deleteCustomer(int id) {
        Customer deletedCustomer = findCustomerById(id);
        customerRepo.delete(deletedCustomer);
        return deletedCustomer;
    }


    @Override
    public List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId) {
        List<Ticket> tickets = ticketRepo.findByCustomerIdAndScreeningId(customerId, screeningId);
        return tickets;
    }

    @Override
    public Ticket createTicket(int customerId, int screeningId, Ticket ticket) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer with that id"));
        Screening screening = screeningRepo.findById(screeningId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Screening with that id"));
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        return ticketRepo.save(ticket);
    }
}
