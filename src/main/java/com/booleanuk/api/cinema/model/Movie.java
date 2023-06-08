package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String rating;
    private String description;
    @Column(name="runtime_mins")
    private int runtimeMins;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"movie"})
    private List<Screening> screenings;
}