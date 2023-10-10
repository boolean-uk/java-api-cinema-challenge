package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "movies")
@JsonIgnoreProperties({"screenings"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String rating;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer runtimeMins;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime updatedAt;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    List<Screening> screenings;
}
