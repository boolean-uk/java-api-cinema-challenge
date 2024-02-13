package com.booleanuk.api.cinema.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Teapot {
    String contents;

    public Teapot(String contents)  {
        this.contents = contents;
    }
}
