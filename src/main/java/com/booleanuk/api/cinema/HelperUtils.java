package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static <T> ResponseEntity<ApiResponse<?>> badRequest(T data) {
        ApiResponse<T> badRequest = new ApiResponse<>("error", data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
    }

    public static <T> ResponseEntity<ApiResponse<?>> createdRequest(T data) {
        ApiResponse<T> createdRequest = new ApiResponse<>("success", data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdRequest);
    }

    public static <T> ResponseEntity<ApiResponse<?>> okRequest(T data) {
        ApiResponse<T> okRequest = new ApiResponse<>("success", data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(okRequest);
    }
}
