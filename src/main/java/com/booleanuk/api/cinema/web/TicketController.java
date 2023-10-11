package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.TicketResponse;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import com.booleanuk.api.cinema.utils.ErrorUtil;
import com.booleanuk.api.cinema.domain.dtos.CreateTicketRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.TicketResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateTicketRequestDTO;
import com.booleanuk.api.cinema.services.TicketService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/customers/{customerId}/screenings/{screeningId}")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketController(TicketService ticketService, ModelMapper modelMapper) {
        this.ticketService = ticketService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/{ticketId}")
    public ResponseEntity<Response<?>> getTicketById(@PathVariable Long ticketId) {
        TicketResponseDTO ticketDTO = ticketService.getTicketById(ticketId);
        return getResponseEntity(ticketDTO);
        // TODO: Add error handling for ticket not belonging to user/screening
    }

    @GetMapping
    public ResponseEntity<Response<?>> getTickets(
            @PathVariable Long customerId,
            @PathVariable Long screeningId
    ) {
        List<TicketResponseDTO> tickets;
        try {
            tickets = ticketService.getTicketsByCustomerAndScreening(customerId, screeningId);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        if (tickets.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.NO_TICKET_FOR_SCREENING_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Response<List<TicketResponseDTO>> response = new Response<>();
        response.set(tickets);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTicket(
            @PathVariable Long customerId,
            @PathVariable Long screeningId,
            @RequestBody @Valid CreateTicketRequestDTO createTicketDTO,
            BindingResult bindingResult) {

        ResponseEntity<Response<?>> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;

        try {
            TicketResponseDTO createdTicket = ticketService.createTicket(customerId, screeningId, createTicketDTO);
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.set(createdTicket);
            return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ErrorResponse errorNotFound = new ErrorResponse();
            errorNotFound.set(e.getMessage());
            return new ResponseEntity<>(errorNotFound, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Response<?>> updateTicket(
            @PathVariable Long ticketId,
            @RequestBody @Valid UpdateTicketRequestDTO updateTicketDTO,
            BindingResult bindingResult) {

        ResponseEntity<Response<?>> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;

        try {
            TicketResponseDTO updatedTicket = ticketService.updateTicket(ticketId, updateTicketDTO);
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.set(updatedTicket);
            return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ErrorResponse errorNotFound = new ErrorResponse();
            errorNotFound.set(e.getMessage());
            return new ResponseEntity<>(errorNotFound, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Response<?>> deleteTicket(@PathVariable Long ticketId, @PathVariable Long customerId) {
        try {
            TicketResponseDTO deletedTicket = ticketService.deleteTicket(ticketId, customerId);
            if (deletedTicket == null) {
                ErrorResponse error = new ErrorResponse();
                error.set(ErrorConstants.TICKET_AND_CUSTOMER_MISMATCH_MESSAGE);
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            return getResponseEntity(deletedTicket);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Response<?>> getResponseEntity(TicketResponseDTO ticket) {
        if (ticket == null) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.TICKET_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.set(ticket);
        return ResponseEntity.ok(ticketResponse);
    }
}
