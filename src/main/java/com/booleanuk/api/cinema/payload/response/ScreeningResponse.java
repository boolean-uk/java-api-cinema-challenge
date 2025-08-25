package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScreeningResponse extends Response<Screening> {
    public ScreeningResponse(Screening data) {
        super(data);
    }
}
