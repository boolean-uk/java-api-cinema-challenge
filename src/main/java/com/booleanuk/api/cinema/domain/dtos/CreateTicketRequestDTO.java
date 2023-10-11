package com.booleanuk.api.cinema.domain.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalDateTime;

@Data

public class CreateTicketRequestDTO {
    @Min(value = 1, message = "Number of seats must be a positive integer")
    private Integer numSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
