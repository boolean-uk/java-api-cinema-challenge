package com.booleanuk.api.cinema.domain.dtos;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UpdateScreeningRequestDTO {
    private Integer screenNumber;
    private Integer capacity;
    private LocalDateTime startsAt;

}