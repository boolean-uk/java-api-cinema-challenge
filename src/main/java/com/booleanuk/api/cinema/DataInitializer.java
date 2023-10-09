package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.domain.dtos.CreateCustomerRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CreateMovieRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CreateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.MovieResponseDTO;
import com.booleanuk.api.cinema.services.CustomerService;
import com.booleanuk.api.cinema.services.MovieService;
import com.booleanuk.api.cinema.services.ScreeningService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DataInitializer {
    private final CustomerService customerService;
    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final Faker faker;

    @Autowired
    public DataInitializer(
            CustomerService customerService,
            MovieService movieService,
            ScreeningService screeningService) {
        this.customerService = customerService;
        this.movieService = movieService;
        this.screeningService = screeningService;
        this.faker = new Faker();
    }


    public void initializeData() {
        for (int i = 0; i < 10; i++) {
            CreateCustomerRequestDTO customerDTO = new CreateCustomerRequestDTO();
            customerDTO.setName(faker.name().fullName());
            customerDTO.setEmail(faker.internet().emailAddress());
            customerDTO.setPhone(faker.phoneNumber().phoneNumber());

//            customerDTO.setCreatedAt(LocalDateTime.now());
//            customerDTO.setUpdatedAt(LocalDateTime.now());

            customerService.createCustomer(customerDTO);
        }

        for (int i = 0; i < 10; i++) {
            CreateMovieRequestDTO movieDTO = new CreateMovieRequestDTO();
            movieDTO.setTitle(faker.book().title());
            movieDTO.setRating(faker.options().option("PG-13", "R", "G"));
            movieDTO.setDescription(faker.lorem().sentence());
            movieDTO.setRuntimeMins(faker.number().numberBetween(60, 180));
//            movieDTO.setCreatedAt(LocalDateTime.now());
//            movieDTO.setUpdatedAt(LocalDateTime.now());
            MovieResponseDTO createdMovie = movieService.createMovie(movieDTO);

            for (int j = 0; j < 3; j++) {
                CreateScreeningRequestDTO screeningDTO = new CreateScreeningRequestDTO();
                screeningDTO.setScreenNumber(faker.number().numberBetween(1, 10));
                screeningDTO.setCapacity(faker.number().numberBetween(20, 100));

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime futureDateTime = now.plusDays(faker.number().numberBetween(1, 30));
                screeningDTO.setStartsAt(futureDateTime);

//                screeningDTO.setCreatedAt(LocalDateTime.now());
//                screeningDTO.setUpdatedAt(LocalDateTime.now());

                screeningService.createScreening(createdMovie.getId(), screeningDTO);
            }
        }
    }
}