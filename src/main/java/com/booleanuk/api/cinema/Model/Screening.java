package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "screen_number")
    private int screenNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "starts_at")
    private String startsAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties("screening")
    @JsonIgnore
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties({"screenings", "movie"})
    @JsonIgnore
    private List<Ticket> tickets;


    public Screening() {
        super();
    }

    public Screening(int screenNumber, int capacity, String startsAt) {
        super();
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setMovie(Movie movie) {
        this.movie = movie;
        movie.getScreenings().add(this);
    }

    public Movie getMovie() {
        return movie;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return getId() == screening.getId() && getScreenNumber() == screening.getScreenNumber() && getCapacity() == screening.getCapacity() && Objects.equals(getStartsAt(), screening.getStartsAt()) && Objects.equals(getCreatedAt(), screening.getCreatedAt()) && Objects.equals(getUpdatedAt(), screening.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getScreenNumber(), getCapacity(), getStartsAt(), getCreatedAt(), getUpdatedAt());
    }
}
