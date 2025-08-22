package com.booleanuk.api.cinema.screening;


import com.booleanuk.api.cinema.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "screen_number")
    private int screen_number;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "starts_at")
    private String starts_at;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties({"screenings", "id"})
    private Movie movie;

    public Screening(int screen_number, int capacity){
        this.screen_number = screen_number;
        this.capacity = capacity;
    }

}
