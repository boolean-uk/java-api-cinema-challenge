package com.booleanuk.api.cinema.Model;


import com.booleanuk.api.cinema.Controller.ScreeningController;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Setter
    @Getter
    @Column(name = "title")
    private String title;
    @Setter
    @Getter
    @Column(name = "rating")
    private String rating;
    @Setter
    @Getter
    @Column(name = "description")
    private String description;
    @Setter
    @Getter
    @Column(name = "runtimeMins")
    private int runtimeMins;

    @Setter
    @Getter
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Setter
    @Getter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;


    @Setter
    @Getter
    @Column(name = "screenings")
    private ArrayList<Screening> screenings;


    /*public Movie(String title, String rating, String description, int runtimeMins, ArrayList<Screening> Screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        this.createdAt = LocalDateTime.parse(currentDateTime.format(formatter));
        this.updatedAt = LocalDateTime.parse(currentDateTime.format(formatter));
        for (int i = 0; i< Screenings.size();i++){
            ScreeningController sc = new ScreeningController();
            sc.create(Screenings.get(i));
        }

    }
    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        this.createdAt = LocalDateTime.parse(currentDateTime.format(formatter));
        this.updatedAt = LocalDateTime.parse(currentDateTime.format(formatter));
    }

*/
}
