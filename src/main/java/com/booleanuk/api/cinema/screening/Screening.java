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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @ManyToOne  //en screening har endast en movie
    @JoinColumn(name = "movie_id", nullable = false)    //sätt FK som heter movie_id som är samma som Movie's id
    @JsonIncludeProperties(value = {"title", "rating", "description", "runTimeMins"})    //inkludera dessa fält från movie vid api anrop
    private Movie movie;

    @OneToMany(mappedBy = "screening")  //en screening kan ha många tickets
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    @PrePersist
    public void onCreate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createdAt = localDateTime;
        this.updatedAt = localDateTime;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.updatedAt = localDateTime;
    }

    public Screening(int screenNumber, int capacity, LocalDateTime startsAt) {  //Ev byta typ till OffsetDateTime
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }


    //LocalDateTime: 2024-02-09T10:50:39.713
    //ZonedDateTime: 2023-03-19 11:30:00+00:00 eller 2024-02-09T13:51:12.356261100+01:00[Europe/Stockholm]
    //OffsetDateTime: 2024-02-09T15:30:00+00:00 eller 2023-03-19T11:30Z (olika pga toString metoden i konsolen)
}
