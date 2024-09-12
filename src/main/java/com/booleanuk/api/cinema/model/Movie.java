package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String rating;
    private String description;
    private Integer runTimeMins;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties(value = {"movie"})
    @JsonIgnore
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, Integer runTimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimeMins = runTimeMins;
    }

    public Movie(String title, String rating, String description, Integer runTimeMins, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimeMins = runTimeMins;
        this.screenings = screenings;
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public Movie(int id){
        this.id = id;
    }

    public Movie(){
        this.createdAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    @JsonIgnore
    public List<Screening> getScreenings() {
        return screenings;
    }

    @JsonProperty
    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }
}
