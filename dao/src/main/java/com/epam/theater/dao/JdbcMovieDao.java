package com.epam.theater.dao;

import com.epam.theater.dao.mapper.MovieMapper;
import com.epam.theater.common.Movie;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcMovieDao extends NamedParameterJdbcDaoSupport implements MovieDao {

    @Resource(name="queries")
    private Map<String, String> queries;

    @Override
    public Movie add(Movie movie) {
        SqlParameterSource parameterSource = getMovieParams(movie);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getNamedParameterJdbcTemplate().update(queries.get("MOVIE_INSERT"), parameterSource, keyHolder);

        int id = getGeneratedId(keyHolder);
        return cloneAndSetId(movie, id);
    }

    @Override
    public List<Movie> getAll() {
        return getJdbcTemplate().query(queries.get("MOVIE_SELECT_ALL"), new MovieMapper());
    }

    @Override
    public Movie getById(int id) {
        Movie movie = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", id);
        try {
            movie = getNamedParameterJdbcTemplate().queryForObject(queries.get("MOVIE_SELECT_BY_ID"), parameterSource, new MovieMapper());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Movie with id: " + id + " not found.");
        }
        return movie;
    }

    @Override
    public Movie update(Movie movie) {
        SqlParameterSource parameterSource = getMovieParams(movie);
        getNamedParameterJdbcTemplate().update(queries.get("MOVIE_UPDATE_BY_ID"), parameterSource);
        return movie;
    }

    @Override
    public boolean delete(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", id);
        int deletedCount = getNamedParameterJdbcTemplate().update(queries.get("MOVIE_DELETE_BY_ID"), parameterSource);
        return deletedCount > 0;
    }

    @Override
    public boolean deleteAll() {
        int deletedCount = getJdbcTemplate().update(queries.get("MOVIE_DELETE_ALL"));
        return deletedCount > 0;
    }

    private SqlParameterSource getMovieParams(Movie movie) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("movie_id", movie.getId());
        params.put("title", movie.getTitle());
        params.put("total_seats", movie.getTotalSeats());
        params.put("free_seats", movie.getFreeSeats());
        params.put("date", movie.getDate());

        return new MapSqlParameterSource(params);
    }

    private Movie cloneAndSetId(Movie movie, int id) {
        Movie movieClone = new Movie();
        movieClone.setId(id);
        movieClone.setTitle(movie.getTitle());
        movieClone.setFreeSeats(movie.getFreeSeats());
        movieClone.setTotalSeats(movie.getTotalSeats());
        movieClone.setDate(movie.getDate());
        return movieClone;
    }

    private int getGeneratedId(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id.intValue();
    }

}