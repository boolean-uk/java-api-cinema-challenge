package com.booleanuk.api.cinema.domain.dtos;

import java.time.LocalDateTime;

public class MovieResponseDTO {
    private Long id;
    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}