package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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
    private int runtimeMins;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "screenings")
    @JsonIncludeProperties(value = {"screen_number", "capacity", "startsAt"})
    private List<Screening> screenings;

    public Movie(int id){
        this.id = id;
    }
    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }

    public List<Screening> getScreenings(){
        return screenings;
    }
    public void setBooks(List<Screening> screenings){
        this.screenings = screenings;
    }
}
