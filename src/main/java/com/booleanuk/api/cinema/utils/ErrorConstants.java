package com.booleanuk.api.cinema.utils;

public class ErrorConstants {
    public static final String BAD_REQUEST_MESSAGE = "Bad Request";
    public static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer Not Found";
    public static final String MOVIE_NOT_FOUND_MESSAGE = "Movie Not Found";
    public static final String TICKET_NOT_FOUND_MESSAGE = "Ticket Not Found";
    public static final String SCREENING_NOT_FOUND_MESSAGE = "Screening Not Found";
    public static final String DATE_TIME_FORMAT_ERROR_MESSAGE = "Bad Request: use ISO-8601 date and time format";
    public static final String SCREENING_NOT_MATCHING_MOVIE_MESSAGE = "Screening does not belong to the specified movie";
    public static final String NO_TICKET_FOR_SCREENING_MESSAGE = "No ticket found for the customer and screening with those ids found";
    public static final String TICKET_AND_CUSTOMER_MISMATCH_MESSAGE = "No such ticket belongs to this user.";
}