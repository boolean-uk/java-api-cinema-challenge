package com.booleanuk.api.cinema.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer screenNumber;

    @Column
    private Integer capacity;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssxxx", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime startsAt;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;

    @Column
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

    @ManyToOne
    @JsonIgnore // This is to not include the movie object in the response
    @JoinColumn(name = "movie_id", nullable = false)
    //@JsonIncludeProperties(value = {"id", "title", "rating", "description", "runtimeMins", "createdAt", "updatedAt"})
    private Movie movie;
}
