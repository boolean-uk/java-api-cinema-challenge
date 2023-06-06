package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.Dtos.CustomerDtoWithoutTickets;
import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ICustomerService implements CustomerServiceInterface {
    @Autowired
    CustomerRepo customerRepo;
    @Override
    public List<CustomerDtoWithoutTickets> generateList() {
        List<Customer> customers =customerRepo.findAll();
        List<CustomerDtoWithoutTickets> theList =new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDtoWithoutTickets dto = new CustomerDtoWithoutTickets(
                    customer.getId(),customer.getName(),customer.getEmail(),customer.getPhone(),customer.getCreatedAt(),customer.getUpdatedAt()
            );
            theList.add(dto);
        }
        return theList;
    }
}
