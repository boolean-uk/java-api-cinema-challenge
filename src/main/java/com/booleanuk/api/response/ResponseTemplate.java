package com.booleanuk.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTemplate {
    private String status;
    private Object data;

    public ResponseTemplate(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
