package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateTicketRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.TicketResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateTicketRequestDTO;
import com.booleanuk.api.cinema.domain.entities.Customer;
import com.booleanuk.api.cinema.domain.entities.Screening;
import com.booleanuk.api.cinema.domain.entities.Ticket;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ScreeningRepository screeningRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(
            TicketRepository ticketRepository,
            CustomerRepository customerRepository,
            ScreeningRepository screeningRepository,
            ModelMapper modelMapper
    ) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.screeningRepository = screeningRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TicketResponseDTO createTicket(Long customerId, Long screeningId, CreateTicketRequestDTO createTicketDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE, customerId)));

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() ->  new ResourceNotFoundException(String.format(ErrorConstants.SCREENING_NOT_FOUND_MESSAGE, screeningId)));

        Ticket ticket = modelMapper.map(createTicketDTO, Ticket.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        ticket.setCreatedAt(currentDateTime);
        ticket.setUpdatedAt(currentDateTime);
        ticket.setCustomer(customer);
        ticket.setScreening(screening);

        Ticket savedTicket = ticketRepository.save(ticket);

        return modelMapper.map(savedTicket, TicketResponseDTO.class);
    }

    @Override
    public List<TicketResponseDTO> getTicketsByCustomerAndScreening(Long customerId, Long screeningId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE, customerId)));

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.SCREENING_NOT_FOUND_MESSAGE, screeningId)));

        List<Ticket> tickets = ticketRepository.findByCustomerAndScreening(customer, screening);

        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDTO getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.TICKET_NOT_FOUND_MESSAGE, ticketId)));
        return modelMapper.map(ticket, TicketResponseDTO.class);
    }

    @Override
    public TicketResponseDTO updateTicket(Long ticketId, UpdateTicketRequestDTO updateTicketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.TICKET_NOT_FOUND_MESSAGE, ticketId)));

        if (hasUpdates(ticket, updateTicketDTO)) {
            if (updateTicketDTO.getNumSeats() != null) {
                ticket.setNumSeats(updateTicketDTO.getNumSeats());
            }

            ticket.setUpdatedAt(LocalDateTime.now());
            Ticket updatedTicket = ticketRepository.save(ticket);

            return modelMapper.map(updatedTicket, TicketResponseDTO.class);
        } else {
            return modelMapper.map(ticket, TicketResponseDTO.class);
        }
    }

    @Override
    public TicketResponseDTO deleteTicket(Long ticketId, Long customerId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.TICKET_NOT_FOUND_MESSAGE, ticketId)));
        if (!ticket.getCustomer().getId().equals(customerId)) {
            return null;
        }
        ticketRepository.delete(ticket);
        return modelMapper.map(ticket, TicketResponseDTO.class);

    }

    private boolean hasUpdates(Ticket existingTicket, UpdateTicketRequestDTO updateTicketDTO) {
        boolean hasUpdates = false;

        if (updateTicketDTO.getNumSeats() != null && !updateTicketDTO.getNumSeats().equals(existingTicket.getNumSeats())) {
            hasUpdates = true;
        }

        return hasUpdates;
    }
}