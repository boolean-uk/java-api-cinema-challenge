package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response<T> {
    @JsonView(Views.BasicInfo.class)
    protected String status;
    @JsonView(Views.BasicInfo.class)
    protected T data;

    public Response(T data){
        this.status = "success";
        this.data = data;
    }

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }
}
