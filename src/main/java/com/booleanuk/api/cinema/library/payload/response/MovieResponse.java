package com.booleanuk.api.cinema.library.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MovieResponse {
    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
