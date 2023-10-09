package com.booleanuk.api.cinema.domain.dtos;

import java.time.LocalDateTime;

public class ScreeningResponseDTO {
    private Long id;
    private Integer screenNumber;
    private Integer capacity;
    private LocalDateTime startsAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long movieId;

}