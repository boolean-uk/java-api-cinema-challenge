package com.booleanuk.api.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        String startsAt = "2023-03-19 11:30:00+00:00";
        String input = startsAt.replace( " " , "T" );
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(input);
        System.out.println("Offsetdatetime:");
        System.out.println(offsetDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("zonedDateTime");
        System.out.println(zonedDateTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime");
        System.out.println(localDateTime);

        /***
         * Offsetdatetime:
         * 2023-03-19T11:30Z
         * zonedDateTime
         * 2024-02-09T13:51:12.356261100+01:00[Europe/Stockholm]
         * localDateTime
         * 2024-02-09T13:51:12.358262500
         */

        SpringApplication.run(Main.class);
    }
}
