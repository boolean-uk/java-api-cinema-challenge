package com.booleanuk.api.cinema.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    private Long id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @Column
    private Integer runtimeMins;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties(value = {"movie"})
    private List<Screening> screenings;
}
