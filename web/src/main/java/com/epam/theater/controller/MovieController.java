package com.epam.theater.controller;

import com.epam.theater.common.Movie;
import com.epam.theater.domain.validator.MovieValidator;
import com.epam.theater.service.MovieService;
import com.epam.theater.service.message.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    @Qualifier(value = "movieService")
    private MovieService movieService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new MovieValidator());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> getAll() {
        return new ResponseEntity<List<Movie>>(movieService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie>getById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<Movie>(movieService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> add(@Valid @RequestBody Movie movie) {
        Movie storedMovie = movieService.add(movie);
        return new ResponseEntity<Movie>(storedMovie, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Movie> update(@Valid @RequestBody Movie movie) {
        Movie updatedMovie = movieService.update(movie);
        return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public StatusMessage delete(@PathVariable(value = "id") int id) {
        boolean isDeleted = movieService.delete(id);
        StatusMessage statusMessage = new StatusMessage();
        if (isDeleted) {
            statusMessage.setStatus(StatusMessage.Status.SUCCESS);
        } else {
            statusMessage.setStatus(StatusMessage.Status.ERROR);
        }
        return statusMessage;
    }

}