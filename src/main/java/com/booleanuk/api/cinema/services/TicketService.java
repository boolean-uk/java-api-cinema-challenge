package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateTicketRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.TicketResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateTicketRequestDTO;

import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket(Long customerId, Long screeningId, CreateTicketRequestDTO createTicketDTO);

    List<TicketResponseDTO> getTicketsByCustomerAndScreening(Long customerId, Long screeningId);

    TicketResponseDTO getTicketById(Long ticketId);

    TicketResponseDTO updateTicket(Long ticketId, UpdateTicketRequestDTO updateTicketDTO);

    TicketResponseDTO deleteTicket(Long ticketId);
}