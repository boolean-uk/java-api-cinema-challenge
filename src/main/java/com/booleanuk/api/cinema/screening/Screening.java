package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime startsAt;

    @Column
    private int capacity;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX") //ALT: "yyyy-MM-dd HH:mm:ssZ" "yyyy-MM-dd HH:mm:ssSSSZ"
    private OffsetDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)   //Behövs ev ej för Java.time
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)    //skapa column som heter movie_id som är samma som Movie movie's @Id
    @JsonIncludeProperties(value = {"title", "rating", "description", "runTimeMins"})    //inkludera dessa fält från movie vid api anrop
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    @PrePersist
    public void onCreate() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Screening(int screenNumber, int capacity, OffsetDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }
}
