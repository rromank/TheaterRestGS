package com.epam.theater.dao;

import com.epam.theater.common.Movie;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class GSMovieDao implements MovieDao {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    GigaSpace space;

    @Override
    public Movie add(Movie movie) {
        space.write(movie);
        return movie;
    }

    @Override
    public List<Movie> getAll() {
        Movie[] movies = space.readMultiple(new Movie());
        return Arrays.asList(movies);
    }

    @Override
    public Movie getById(int id) {
        return space.readById(Movie.class, id);
    }

    @Override
    public Movie update(Movie movie) {
        return add(movie);
    }

    @Override
    public boolean delete(int id) {
        Movie template = new Movie();
        template.setId(id);
        return space.clear(template) > 0;
    }

    @Override
    public boolean deleteAll() {
        return space.clear(new Movie()) > 0;
    }

}