package com.booleanuk.api.cinema.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateCreater {

    public static String getCurrentDate() {

        return new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss+00:00").format(Calendar.getInstance().getTime());
    }


}