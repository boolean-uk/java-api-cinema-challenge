package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Screening;
import java.util.List;

public class ScreeningListResponse extends Response<List<Screening>> {
    private List<Screening> data;
    public ScreeningListResponse(List<Screening> data) {
        super("success", data);
    }
}
