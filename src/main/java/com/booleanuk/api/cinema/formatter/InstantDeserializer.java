package com.booleanuk.api.cinema.formatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class InstantDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String dateString = parser.getText();
        try {
            return FORMATTER.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
