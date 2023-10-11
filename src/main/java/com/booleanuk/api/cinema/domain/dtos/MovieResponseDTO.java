package com.booleanuk.api.cinema.domain.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovieResponseDTO {
    private Long id;
    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}