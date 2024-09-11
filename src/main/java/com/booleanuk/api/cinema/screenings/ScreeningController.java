package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies")
public class ScreeningController extends GenericController<Screening> {

}
