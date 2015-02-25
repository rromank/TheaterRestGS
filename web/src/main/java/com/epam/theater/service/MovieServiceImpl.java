package com.epam.theater.service;

import com.epam.theater.dao.MovieDao;
import com.epam.theater.common.Movie;
import com.epam.theater.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public Movie getById(int id) {
        return getMovie(id);
    }

    @Override
    public Movie update(Movie movie) {
        return movieDao.update(movie);
    }

    @Override
    public boolean delete(int id) {
        return movieDao.delete(id);
    }

    private Movie getMovie(int id) {
        Movie movie = movieDao.getById(id);
        if (movie == null) {
            throw new ServiceException("movie with this id is not exists");
        }
        return movie;
    }

}