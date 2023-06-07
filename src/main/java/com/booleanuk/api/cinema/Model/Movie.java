package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runtime_mins")
    private int runtimeMins;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;


    public Movie() {
        super();
    }

    public Movie(String title, String rating, String description, int runtimeMins) {
        super();
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
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

    public int getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(int runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getId() == movie.getId() && getRuntimeMins() == movie.getRuntimeMins() && Objects.equals(getTitle(), movie.getTitle()) && Objects.equals(getRating(), movie.getRating()) && Objects.equals(getDescription(), movie.getDescription()) && Objects.equals(getCreatedAt(), movie.getCreatedAt()) && Objects.equals(getUpdatedAt(), movie.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getRating(), getDescription(), getRuntimeMins(), getCreatedAt(), getUpdatedAt());
    }
}
