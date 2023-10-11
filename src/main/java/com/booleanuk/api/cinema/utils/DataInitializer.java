package com.booleanuk.api.cinema.utils;

import com.booleanuk.api.cinema.domain.dtos.*;
import com.booleanuk.api.cinema.services.CustomerService;
import com.booleanuk.api.cinema.services.MovieService;
import com.booleanuk.api.cinema.services.ScreeningService;
import com.booleanuk.api.cinema.services.TicketService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class DataInitializer {
    private final CustomerService customerService;
    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final TicketService ticketService;
    private final Faker faker;

    @Autowired
    public DataInitializer(CustomerService customerService, MovieService movieService, ScreeningService screeningService, TicketService ticketService) {
        this.customerService = customerService;
        this.movieService = movieService;
        this.screeningService = screeningService;
        this.ticketService = ticketService;
        this.faker = new Faker();
    }

    public void initializeData() {
        for (int i = 0; i < 10; i++) {
            CreateCustomerRequestDTO customerDTO = new CreateCustomerRequestDTO();
            customerDTO.setName(faker.name().fullName());
            customerDTO.setEmail(faker.internet().emailAddress());
            customerDTO.setPhone(faker.phoneNumber().phoneNumber());
            customerService.createCustomer(customerDTO);
        }
        List<ScreeningResponseDTO> screenings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CreateMovieRequestDTO movieDTO = new CreateMovieRequestDTO();
            movieDTO.setTitle(faker.book().title());
            movieDTO.setRating(faker.options().option(MovieRatings.PG_13,MovieRatings.R,MovieRatings.G));
            movieDTO.setDescription(faker.lorem().sentence());
            movieDTO.setRuntimeMins(faker.number().numberBetween(60, 180));
            MovieResponseDTO createdMovie = movieService.createMovie(movieDTO);

            for (int j = 0; j < 3; j++) {
                CreateScreeningRequestDTO screeningDTO = new CreateScreeningRequestDTO();
                screeningDTO.setScreenNumber(faker.number().numberBetween(1, 10));
                screeningDTO.setCapacity(faker.number().numberBetween(20, 100));

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime futureDateTime = now.plusDays(faker.number().numberBetween(1, 30));
                screeningDTO.setStartsAt(futureDateTime);
                ScreeningResponseDTO screening = screeningService.createScreening(createdMovie.getId(), screeningDTO);
                screenings.add(screening);
            }
        }
        addTickets(screenings);
    }

    private void addTickets(List<ScreeningResponseDTO> screenings) {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();

        for (CustomerResponseDTO customer : customers) {
            for (int i = 0; i < 3; i++) { // Add three random screenings for each customer
                ScreeningResponseDTO randomScreening = getRandomScreening(screenings);
                CreateTicketRequestDTO ticketDTO = new CreateTicketRequestDTO();
                ticketDTO.setNumSeats(faker.number().numberBetween(1, randomScreening.getCapacity()));
                ticketService.createTicket(customer.getId(), randomScreening.getId(), ticketDTO);
            }
        }
    }

    private ScreeningResponseDTO getRandomScreening(List<ScreeningResponseDTO> screenings) {
        Random random = new Random();
        int randomIndex = random.nextInt(screenings.size());
        return screenings.get(randomIndex);
    }
}