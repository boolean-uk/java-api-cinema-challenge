package com.booleanuk.api.cinema.screens;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.tickets.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "screen_number")
    private int screen_number;

    @Column(name = "starts_at")
    private String starts_at;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @OneToMany(mappedBy = "screen")
    @JsonIgnoreProperties({"id", "screen"})
    private List<Ticket> tickets;

    public Screen(int id) {
        this.id = id;
    }

}