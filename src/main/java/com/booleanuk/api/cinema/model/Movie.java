package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runTimesMins")
    private int runTimesMins;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;

   // @OneToMany(mappedBy = "movie")
   // @JsonIgnoreProperties("movie")
   // private List<Ticket> tickets;

    public Movie() {}

    public Movie(Integer id, String title, String rating, String description, int runTimesMins, Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimesMins = runTimesMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Movie(String title, String rating, String description, int runTimesMins, Date createdAt, Date updatedAt) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimesMins = runTimesMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Movie(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRunTimesMins() {
        return runTimesMins;
    }

    public void setRunTimesMins(int runTimesMins) {
        this.runTimesMins = runTimesMins;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

   /* public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    */
}
