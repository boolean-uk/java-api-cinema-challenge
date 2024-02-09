package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    @Builder.Default
    @JsonIncludeProperties(value = {"id", "screenNumber", "capacity", "startsAt"})
    @OneToMany(mappedBy = "movie")
    private List<Screening> screenings = new ArrayList<>();
}
