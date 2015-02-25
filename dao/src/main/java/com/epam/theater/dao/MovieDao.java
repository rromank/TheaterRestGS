package com.epam.theater.dao;

import com.epam.theater.common.Movie;

import java.util.List;

public interface MovieDao {

    public Movie add(Movie movie);

    public List<Movie> getAll();

    public Movie getById(int id);

    public Movie update(Movie movie);

    public boolean delete(int id);

    public boolean deleteAll();

}