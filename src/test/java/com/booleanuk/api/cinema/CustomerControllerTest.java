package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testGetAllCustomers() throws Exception {
        customerRepository.deleteAll();
        Customer c1 = customerRepository.save(new Customer("Jane Doe", "jane@doe.com", "123456789"));
        Customer c2 = customerRepository.save(new Customer("John Smith", "john@smith.com", "987654321"));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Jane Doe"))
                .andExpect(jsonPath("$.data[0].email").value("jane@doe.com"))
                .andExpect(jsonPath("$.data[0].phone").value("123456789"))
                .andExpect(jsonPath("$.data[1].name").value("John Smith"))
                .andExpect(jsonPath("$.data[1].email").value("john@smith.com"))
                .andExpect(jsonPath("$.data[1].phone").value("987654321"))
                .andExpect(jsonPath("$.data[0].id").value(c1.getId()))
                .andExpect(jsonPath("$.data[1].id").value(c2.getId()));
    }

    @Test
    public void testCreateACustomer() throws Exception {
        Customer validCustomer = new Customer("Jane Doe", "jane@doe.com", "123456789");
        Customer invalidCustomer = new Customer("John Smith", "john@smith.com", "987654321");

        invalidCustomer.setPhone(null);

        String validRequestBody = objectMapper.writeValueAsString(validCustomer);
        String invalidRequestBody = objectMapper.writeValueAsString(invalidCustomer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name").value("Jane Doe"))
                .andExpect(jsonPath("$.status").value("success"));

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("bad request"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = customerRepository.save(new Customer("Jane Doe", "jane@doe.com", "123456789"));

        HashMap<String, String> update = new HashMap<>();
        update.put("name", "Dracula");
        update.put("email", "dracula@gmail.com");

        String requestBody = objectMapper.writeValueAsString(update);

        mockMvc.perform(put("/customers/{id}", customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("Dracula"))
                .andExpect(jsonPath("$.data.email").value("dracula@gmail.com"))
                .andExpect(jsonPath("$.data.phone").value("123456789"));

        customerRepository.delete(customer);

        mockMvc.perform(put("/customers/{id}", customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("not found"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = customerRepository.save(new Customer("Jane Doe", "jane@doe.com", "123456789"));

        mockMvc.perform(delete("/customers/{id}", customer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("Jane Doe"))
                .andExpect(jsonPath("$.data.email").value("jane@doe.com"))
                .andExpect(jsonPath("$.data.phone").value("123456789"));

        mockMvc.perform(delete("/customers/{id}", customer.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("not found"));
    }


}
