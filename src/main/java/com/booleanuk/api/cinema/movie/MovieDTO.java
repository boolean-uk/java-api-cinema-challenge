// MovieDTO.java
package com.booleanuk.api.cinema.movie;

//import com.booleanuk.api.cinema.screening.ScreeningDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieDTO {
    private int id;
    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //private List<ScreeningDTO> screenings; // Exclude tickets to break the circular reference
}
