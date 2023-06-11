package com.booleanuk.api.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }
}

//TODO: fix bug where I can't post. First post requests try to create an entity with id that
// already exists cause what I insert through flyway isn't calculated by Hibernate. So first
// post request while incrementing the id until it does not already exist fail