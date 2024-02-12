package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "description", "runtimeMins"})
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    public Screening(int id){
        setId(id);
    }

    public Screening(int screenNumber, int capacity, String startsAt) throws DateTimeParseException{
        setScreenNumber(screenNumber);
        setCapacity(capacity);
        setStartsAt(LocalDateTime.parse(startsAt));
    }
}
