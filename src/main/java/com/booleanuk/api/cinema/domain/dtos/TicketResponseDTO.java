package com.booleanuk.api.cinema.domain.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TicketResponseDTO {
    private Long id;
    private int numSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}