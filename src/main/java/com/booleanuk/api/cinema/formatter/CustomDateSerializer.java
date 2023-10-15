package com.booleanuk.api.cinema.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.cglib.core.Local;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class CustomDateSerializer extends JsonSerializer<Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String dateString = dateFormat.format(value);
        gen.writeString(dateString);

    }
}

