package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String rating;
    private String description;
    private Integer runTimeMins;
    private String createdAt;
    private String updatedAt;

    public Movie(String title, String rating, String description, Integer runTimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimeMins = runTimeMins;
    }

    public Movie(int id){
        this.id = id;
    }
}
