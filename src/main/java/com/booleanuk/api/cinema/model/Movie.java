package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.StaticMethods;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String rating;
    private String description;
    @Column(name = "runtime_minutes")
    private int runtimeMins;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;
    @OneToMany(mappedBy = "moviePlaying")
    @JsonIgnoreProperties("moviePlaying")
    private List<Screening> screenings;
    public Movie() {
    }

    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        //initialising the date and time when object is created
        this.createdAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());

    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setUpdatedAt() {
        //when this method is called, the updatedAt value changes
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public int getRuntimeMins() {
        return runtimeMins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuntimeMins(int runtimeMins) {
        this.runtimeMins = runtimeMins;
    }
    public void addScreening(Screening screening){
        this.screenings.add(screening);
    }
}
