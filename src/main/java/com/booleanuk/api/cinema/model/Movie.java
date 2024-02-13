package com.booleanuk.api.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
    private int runtimeMins;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Screening> screenings;

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Movie(String title, String rating, String description){
        this.title = title;
        this.rating = rating;
        this.description = description;
    }
}
