package com.booleanuk.api.cinema.web;


import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.TicketResponse;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import java.util.stream.Collectors;

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
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getTickets(
            @PathVariable Long customerId,
            @PathVariable Long screeningId
    ) {
        List<TicketResponseDTO> tickets = ticketService.getTicketsByCustomerAndScreening(customerId, screeningId);
        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTicket(
            @PathVariable Long customerId,
            @PathVariable Long screeningId,
            @RequestBody @Valid CreateTicketRequestDTO createTicketDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        try {
            TicketResponseDTO createdTicket = ticketService.createTicket(customerId, screeningId, createTicketDTO);
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.set(createdTicket);
            return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
//            error.set(ErrorConstants.TICKET_NOT_FOUND_MESSAGE);
            // TODO: This should be a different error message

            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Response<?>> updateTicket(
            @PathVariable Long ticketId,
            @RequestBody @Valid UpdateTicketRequestDTO updateTicketDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        try {
            TicketResponseDTO updatedTicket = ticketService.updateTicket(ticketId, updateTicketDTO);
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.set(updatedTicket);
            return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Response<?>> deleteTicket(@PathVariable Long ticketId) {
        try {
            TicketResponseDTO deletedTicket = ticketService.deleteTicket(ticketId);
            return getResponseEntity(deletedTicket);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
    // Todo: make update get set at create and not just at update
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
