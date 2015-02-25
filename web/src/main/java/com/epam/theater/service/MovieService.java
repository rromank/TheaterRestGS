package com.epam.theater.service;

import com.epam.theater.common.Movie;

import java.util.List;

public interface MovieService {

    public Movie add(Movie movie);

    public List<Movie> getAll();

    public Movie getById(int id);

    public Movie update(Movie movie);

    public boolean delete(int id);

}