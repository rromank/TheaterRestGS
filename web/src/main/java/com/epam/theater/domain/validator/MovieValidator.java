package com.epam.theater.domain.validator;

import com.epam.theater.common.Movie;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MovieValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Movie.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        Movie movie = (Movie) object;

        if (isNotValidTitle(movie)) {
            errors.rejectValue("title", "title length can't be less than 3 symbols");
        }
        if (isNotValidTotalSeats(movie)) {
            errors.rejectValue("totalSeats", "total_seats invalid value");
        }
        if (isNotValidFreeSeats(movie)) {
            errors.rejectValue("freeSeats", "free_seats can't be more than total_seats");
        }
    }

    private boolean isNotValidFreeSeats(Movie movie) {
        return movie.getTotalSeats() < movie.getFreeSeats();
    }

    private boolean isNotValidTotalSeats(Movie movie) {
        return movie.getTotalSeats() <= 0;
    }

    private boolean isNotValidTitle(Movie movie) {
        return movie.getTitle() == null || movie.getTitle().length() < 3;
    }

}