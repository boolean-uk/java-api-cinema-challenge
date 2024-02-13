package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;

public class HelperUtils {
    /**
     * Private Constructor to avoid instantiation as class holds static helper methods
     */
    private HelperUtils() {}

    public static boolean invalidMovieFields(Movie m) {
        return  m.getTitle() == null ||
                m.getRating() == null ||
                m.getDescription() == null ||
                m.getRuntimeMinutes() == 0;
    }

    public static boolean invalidScreeningFields(Screening s) {
        return  s.getMovie() == null ||
                s.getScreenNumber() < 1 ||
                s.getCapacity() < 1 ||
                s.getStartsAt() == null;
    }

    public static boolean invalidCustomerFields(Customer c) {
        return  c.getName() == null ||
                c.getEmail() == null ||
                c.getPhone() == null;
    }
}
