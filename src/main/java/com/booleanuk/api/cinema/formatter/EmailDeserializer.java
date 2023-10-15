package com.booleanuk.api.cinema.formatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.regex.Pattern;

public class EmailDeserializer extends JsonDeserializer<String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String value = p.getValueAsString();
        if (isValidEmail(value)) {
            return value;
        } else {
            throw new RuntimeException("Given email does not have a valid formatting");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
