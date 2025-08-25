package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Entity
@Table(name = "screenings")
@Data
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Detailed.class)
    private int id;

    @Column
    @JsonView(Views.BasicInfo.class)
    private int screenNumber;

    @Column
    @JsonView(Views.BasicInfo.class)
    private int capacity;

    @Column
    @JsonView(Views.BasicInfo.class)
    private OffsetDateTime startsAt;


    @Column
    @JsonView(Views.Detailed.class)
    private OffsetDateTime createdAt;

    @Column
    @JsonView(Views.Detailed.class)
    private OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id")
//    @JsonProperty("movie_id")
//    @JsonIgnoreProperties({"movie_id"})
    @JsonIgnore
    private Movie movie;

    @PrePersist
    public void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Screening(int id) {
        this.id = id;
    }

    public Screening(int screenNumber, OffsetDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;

        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public Screening(int screenNumber, OffsetDateTime startsAt, int capacity, Movie movie) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.movie = movie;
    }

    //    TODO link back to movies and then to tickets
}
