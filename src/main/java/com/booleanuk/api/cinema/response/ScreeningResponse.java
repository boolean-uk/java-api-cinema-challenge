package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Screening;

public class ScreeningResponse extends Response<Screening> {
    private Screening data;
    public ScreeningResponse(Screening screening) {
        super("success", screening);
    }
}
