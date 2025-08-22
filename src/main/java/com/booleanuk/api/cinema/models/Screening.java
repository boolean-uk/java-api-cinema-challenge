package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="movie_id")
    @JsonIgnore
    Movie movie;

    @NotNull
    Integer screenNumber;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties({"screening"})
    List<Ticket> tickets;

    @NotNull
    @JsonProperty("startsAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX") // Accept "2023-03-19 11:30:00+00:00"    OffsetDateTime startsAt;
    OffsetDateTime startsAt;

    @NotNull
    Integer capacity;

    OffsetDateTime created;

    OffsetDateTime updatedAt;

    public Screening(int screenNumber, OffsetDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }
}
