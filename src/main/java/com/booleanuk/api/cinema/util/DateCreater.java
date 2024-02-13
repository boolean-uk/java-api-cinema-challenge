package com.booleanuk.api.cinema.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateCreater {

    public static String getCurrentDate() {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy - HH:mm:ss Z");

        return zonedDateTimeNow.format(formatter);
    }


    public static void main(String[] args) {
        System.out.println(getCurrentDate());
    }
}