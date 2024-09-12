package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer screenNumber;
    private Integer capacity;
    private OffsetDateTime startsAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties(value = {"screenings"})
    @JsonIgnore
    private Movie movie;

    public Screening(Integer screenNumber, Integer capacity, OffsetDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public Screening(int id){
        this.id = id;
    }

    public Screening(){
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
