package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.screening.Screening;

import java.util.List;

public class ScreeningListResponse extends Response<List<Screening>> {
    public ScreeningListResponse (List<Screening> data) {
        super("success", data);
    }
}
