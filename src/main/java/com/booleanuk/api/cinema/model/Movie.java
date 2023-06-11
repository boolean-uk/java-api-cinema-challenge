package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
public class Movie extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonProperty(index = 1)
    private long id;
    @NotNull
    @JsonProperty(index = 2)
    private String title;
    @NotNull
    @JsonProperty(index = 3)
    private String rating;
    @NotNull
    @JsonProperty(index = 4)
    private String description;
    @NotNull
    @JsonProperty(index = 5)
    private Integer runtimeMins;

    @OneToMany(mappedBy = "movie")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //@JsonIgnore
    List<Screening> screenings;

    public Movie() {
    }

    public long getId() {
        return id;
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

    public int getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(int runtimeMins) {
        this.runtimeMins = runtimeMins;
    }
}
