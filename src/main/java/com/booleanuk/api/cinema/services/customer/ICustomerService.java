package com.booleanuk.api.cinema.services.customer;

import com.booleanuk.api.cinema.Dtos.customers.CustomerDtoWithoutTickets;
import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ICustomerService implements CustomerServiceInterface {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<CustomerDtoWithoutTickets> generateList() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDtoWithoutTickets> theList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDtoWithoutTickets dto = new CustomerDtoWithoutTickets(
                    customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getCreatedAt(), customer.getUpdatedAt()
            );
            theList.add(dto);
        }
        return theList;
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
    public CustomerDtoWithoutTickets generateCustomerWithoutTickets(Customer customer) {
        return new CustomerDtoWithoutTickets(
                customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getCreatedAt(), customer.getUpdatedAt()
        );
    }
}
