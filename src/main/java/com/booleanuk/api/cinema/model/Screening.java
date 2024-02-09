package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    // Don't want the movie_id to be shown in the response.
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    private int capacity;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    // Pattern made to match the date format for post request in spec.
    // Differs from the expected response date format in the spec.
    private Date startsAt;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    public Screening(int screenNumber, int capacity,  Date startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }
}
