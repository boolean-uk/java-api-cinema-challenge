package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Integer id;

    @Column(name = "screen_number")
    private int screenNumber;
    @Column(name = "capacity")
    private int capacity;


    @Column(name = "starts_at")
    private String startsAt;
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    public Screening() {

    }

    public Screening(int id) {
        this.id = id;
    }

    public Screening(Integer id, int screenNumber, int capacity, String startsAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
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