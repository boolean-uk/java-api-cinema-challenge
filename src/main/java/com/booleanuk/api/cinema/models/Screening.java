package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;

    @Column
    private int capacity;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private String startsAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"screening"})
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "runtimeMins"})
    private Movie movie;

    public Screening(int screenNumber, int capacity, String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = OffsetDateTime.now().toString();
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
