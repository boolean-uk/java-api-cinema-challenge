package com.booleanuk.api.cinema;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class StaticMethods {
    public static String convertEpochTimeToDateTime(long epochTimeInMillis) {
        Instant instant = Instant.ofEpochMilli(epochTimeInMillis);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

}
