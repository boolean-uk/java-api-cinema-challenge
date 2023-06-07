package com.booleanuk.api.cinema;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeGenerator {

    public static @NotNull String getDateTimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = now.format(format);
        return dateTime;
    }

}
