package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.TicketDto;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ApiException;
import com.booleanuk.api.cinema.response.ApiSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    TicketRepository repository;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<TicketDto>>> getScreeningsForCustomer(@PathVariable int customerId, @PathVariable int screeningId) {
        List<TicketDto> tickets = this.repository.findByCustomerIdAndScreeningId(customerId, screeningId);
        if (tickets.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not found");
        }
        return ResponseEntity.ok(new ApiSuccessResponse<>(tickets));
    }
}
