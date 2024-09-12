package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String rating;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int runtimeMinutes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"movie", "id", "createdAt", "updatedAt"})
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMinutes) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMinutes = runtimeMinutes;
    }

    public Movie(int id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.screenings = getScreenings();
        this.createdAt = getCreatedAt();
        this.updatedAt = LocalDateTime.now();
    }

    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                ", runtimeMinutes=" + runtimeMinutes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
