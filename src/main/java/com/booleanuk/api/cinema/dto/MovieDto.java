package com.booleanuk.api.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieDto {

    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MovieDto(int id, String title, String rating, String description, int runtimeMins,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
