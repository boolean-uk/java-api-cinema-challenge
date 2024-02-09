package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title", "rating", "description", "runtimeMins"})
    private Movie movie;
    private Integer screenNumber;
    private Integer capacity;
    private ZonedDateTime startsAt;
}
