package com.booleanuk.api.cinema.Screening;

import com.booleanuk.api.cinema.Movie.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen_number")
    private int screenNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "starts_at")
    private Date startsAt;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Screening(int screenNumber, int capacity, Date startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }
}
