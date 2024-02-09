package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;
    @Builder.Default
    private final ZonedDateTime createdAt = ZonedDateTime.now();
    @Builder.Default
    private ZonedDateTime updatedAt = ZonedDateTime.now();
    @ToString.Exclude
    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties(value = "movie")
    @Builder.Default
    private List<Screening> screenings = new ArrayList<>();
}
