package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonIgnore
    private List<Screening> screenings = new ArrayList<>();

    public Movie(String title, String rating, String description, Integer runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = new ArrayList<>();
    }

    public Movie(String title, String rating, String description, Integer runtimeMins, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.screenings = new ArrayList<>();
        this.runtimeMins = runtimeMins;
        this.screenings = screenings;
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

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }


    public void setTime() {
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        this.createdAt = timestamp;
        this.updatedAt = timestamp;
    }
    public void updateUpdatedAt() {
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        this.updatedAt = timestamp;
    }
}
