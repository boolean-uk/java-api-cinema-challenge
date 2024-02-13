package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Column
    private int screenNumber;

    @Column
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name ="movie_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "rating", "description", "runtimeMins"})
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    public Screening(int screenNumber, LocalDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public Screening(int id) {
        this.id = id;
    }

    public int getNumberOfBookedSeats(){
        int res = 0;
        if (tickets != null) {
            for (Ticket ticket : this.getTickets()) {
                res += ticket.getNumSeats();
            }
        }
        return res;
    }

}