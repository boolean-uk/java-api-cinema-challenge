package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int screenNumber;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime startsAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties({"title", "rating", "description", "runtimeMinutes"})
    private Movie movie;

    public Screening(int screenNumber, int capacity, OffsetDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    public Screening(int id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneId.systemDefault());
        this.updatedAt = OffsetDateTime.now(ZoneId.systemDefault());
    }

    @PreUpdate
    protected void onUpdate() {
        this.createdAt = this.getCreatedAt();
        this.updatedAt = OffsetDateTime.now(ZoneId.systemDefault());
    }
}
