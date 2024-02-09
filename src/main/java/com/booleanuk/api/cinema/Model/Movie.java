package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
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
    private Integer runtimeMins;
    @Column
    private String createdAt;
    @Column
    private String updatedAt;


    //@OneToMany(mappedBy = "movie")
    // @JsonIncludeProperties(value = {"screenNumber", "startsAt", "capacity"})
    //private List<Screening> screenings;

    public Movie(String title, String rating, String description, Integer runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        String datetimeNow = LocalDateTime.now().toString();
        this.createdAt = datetimeNow;
        this.updatedAt = datetimeNow;
    }

    public Movie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(Integer runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt() {
        this.updatedAt = LocalDateTime.now().toString();
    }
}
