package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public MovieDTO(Movie movie) {
        this.id=movie.getId();
        this.title = movie.getTitle();
        this.rating = movie.getRating();
        this.description = movie.getDescription();
        this.runtimeMins = movie.getRuntimeMins();
        this.createdAt = movie.getCreatedAt();
        this.updatedAt = movie.getUpdatedAt();
    }


}
