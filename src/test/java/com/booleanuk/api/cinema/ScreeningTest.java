package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static java.sql.Date.*;

public class ScreeningTest {
    @Test
    public void testVerifyScreening()   {
        Date date = valueOf(LocalDate.now());
        Movie movie = new Movie(
                "Dodgeball",
                "PG-13",
                "The greatest movie of all time.",
                126
        );

        Screening validScreening = new Screening(
                movie, 5, date, 40
        );
        Screening invalidScreening = new Screening();

        Assertions.assertTrue(validScreening.verifyScreening());
        Assertions.assertFalse(invalidScreening.verifyScreening());
    }
}
