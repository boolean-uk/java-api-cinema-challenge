package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movies_id")
    private Integer id;

    @Column(name = "title")
    private String title;
    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runtimemins")
    private Integer runtimeMins;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    public Movie() {

    }

    public Movie(int id) {
        this.id = id;
    }

    public Movie(Integer id, String title, String rating, String description, Integer runtimeMins) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}