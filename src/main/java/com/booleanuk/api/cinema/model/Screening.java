package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "screen_number")
    private int screenNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "starts_at")
    private String startsAt;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title", "description", "runtime_mins"})
    private Movie movie;

    public Screening(int screen_number, int capacity, String starts_at) {
        this.screenNumber = screen_number;
        this.capacity = capacity;
        this.startsAt = starts_at;
    }

    public Screening(int id) {
        this.id = id;
    }
}
