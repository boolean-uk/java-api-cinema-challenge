package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    private Integer screenNumber;
    private Integer capacity;
    private ZonedDateTime startsAt;
    @Builder.Default
    private final ZonedDateTime createdAt = ZonedDateTime.now();
    @Builder.Default
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}
