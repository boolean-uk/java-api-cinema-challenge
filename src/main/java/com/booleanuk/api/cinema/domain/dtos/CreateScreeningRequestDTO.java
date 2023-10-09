package com.booleanuk.api.cinema.domain.dtos;

import java.time.LocalDateTime;

public class CreateScreeningRequestDTO {
    private Integer screenNumber;
    private Integer capacity;
    private LocalDateTime startsAt;
    private Long movieId;

}