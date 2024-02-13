package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @Column
    private int runTimeMins;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Screening> screenings;

    public Movie() {
        this.screenings = new ArrayList<>();
    }

    public Movie(String title, String rating, String description, int runTimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimeMins = runTimeMins;
    }

    public Movie(int id) {
        this.id = id;
    }
}
