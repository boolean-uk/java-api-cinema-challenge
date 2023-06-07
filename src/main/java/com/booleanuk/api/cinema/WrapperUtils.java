package com.booleanuk.api.cinema;

import org.springframework.stereotype.Component;

public class WrapperUtils {
    public enum STATUS {
        SUCCESS("success"),
        FAILURE("failure");

        public final String status;

        STATUS(String status) {
            this.status = status;
        }
    };
}
