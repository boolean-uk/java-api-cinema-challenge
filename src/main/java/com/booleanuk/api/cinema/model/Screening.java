package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer screenNumber;

    @Column
    private LocalDateTime startsAt;

    @Column
    private Integer capacity;

    //TODO: fix so ouput when get request is correct
  //  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title", "rating", "description", "runtimeMins"})
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Ticket> tickets;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public Screening(int screenNumber, LocalDateTime startsAt) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
    }


    public void setStartsAt(String startsAt) {
        // Define the formatter for the input format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

        // Parse the date-time string into a ZonedDateTime object
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(startsAt, formatter);

        // Convert ZonedDateTime to LocalDateTime by removing timezone information
        this.startsAt = zonedDateTime.toLocalDateTime();
    }
//
//    public String getStartsAt() {
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        return startsAt.format(outputFormatter);
//    }
//
//    public String getCreatedAt() {
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        return createdAt.format(outputFormatter);
//    }
//
//    public String getUpdatedAt() {
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        return updatedAt.format(outputFormatter);
//    }
}

