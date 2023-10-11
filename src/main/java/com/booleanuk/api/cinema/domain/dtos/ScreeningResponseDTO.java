package com.booleanuk.api.cinema.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ScreeningResponseDTO {
    private Long id;
    private Integer screenNumber;
    private Integer capacity;
    private LocalDateTime startsAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    private Long movieId;

}