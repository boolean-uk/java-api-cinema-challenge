package com.booleanuk.api.cinema.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String displayRating;

    public static boolean isValidRating(String ratingStr) {
        for (Rating rating : Rating.values()) {
            if (rating.getDisplayRating().equals(ratingStr)) {
                return true;
            }
        }
        log.debug(ratingStr + " is not recognised as a rating");
        return false;
    }
}
