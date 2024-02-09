package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    @Temporal(TemporalType.TIMESTAMP)   //Test
    private Date startsAt;

    @Column
    private int capacity;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne  //en screening har endast en movie
    @JoinColumn(name = "movie_id", nullable = false)    //sätt FK som heter movie_id som är samma som Movie's id
    @JsonIncludeProperties(value = {"title", "rating", "description", "runTimeMins"})    //inkludera dessa fält från movie
    private Movie movie;

    @PrePersist
    public void onCreate() {
        Date creationDate = new Date();
        this.createdAt = creationDate;
        this.updatedAt = creationDate;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();
    }

    public Screening(int screenNumber, int capacity, Date startsAt) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }
}
