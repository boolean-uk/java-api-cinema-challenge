package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Screening;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class ScreeningListResponse extends Response<List<Screening>> {
    public ScreeningListResponse(List<Screening> data) {
        super(data);
    }
}
