package com.epam.theater.service;

import com.epam.theater.common.Movie;
import com.epam.theater.dao.MovieDao;
import com.epam.theater.service.exception.ServiceException;
import org.openspaces.remoting.RemotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@RemotingService
public class MovieServiceImpl implements MovieService {

    @Autowired
    @Qualifier(value = "movieDao")
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