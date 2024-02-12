package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;



    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMins){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = currentDateTime;
        this.updatedAt = this.createdAt;
    }

}
