package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.models.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MovieTest {
    @Test
    public void testVerifyMovie()   {
        Movie validMovie = new Movie(
                "Dodgeball",
                "PG-13",
                "The greatest movie of all time.",
                126
        );
        Movie invalidMovie = new Movie();

        Assertions.assertTrue(validMovie.verifyMovie());
        Assertions.assertFalse(invalidMovie.verifyMovie());
    }
}
