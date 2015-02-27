package com.epam.theater.service;

import com.epam.theater.common.Movie;
import com.epam.theater.common.Ticket;
import com.epam.theater.dao.MovieDao;
import com.epam.theater.dao.TicketDao;
import com.epam.theater.service.exception.ServiceException;
import org.openspaces.remoting.RemotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@RemotingService
public class TicketServiceImpl implements TicketService {

    @Autowired
    @Qualifier(value = "ticketDaoGS")
    private TicketDao ticketDao;

    @Autowired
    @Qualifier(value = "movieDaoGS")
    private MovieDao movieDao;

    @Override
    public Ticket buy(int movieId) {
        Movie movie = getMovie(movieId);

        checkFreeSeats(movie);
        decreaseFreeSeats(movie);
        int unusedSeatNumber = ticketDao.getUnusedSeatNumber(movie.getId());
        Ticket ticket = new Ticket();
        ticket.setMovie(movie);
        ticket.setSeatNumber(unusedSeatNumber);

        return ticketDao.add(ticket);
    }

    @Override
    public boolean delete(int id) {
        Ticket ticket = getTicket(id);
        Movie movie = ticket.getMovie();
        increaseFreeSeats(movie);
        return ticketDao.delete(id);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    private void decreaseFreeSeats(Movie movie) {
        if (movie.getFreeSeats() > 0) {
            movie.setFreeSeats(movie.getFreeSeats() - 1);
            movieDao.update(movie);
        }
    }

    private void increaseFreeSeats(Movie movie) {
        if (movie.getFreeSeats() < movie.getTotalSeats()) {
            movie.setFreeSeats(movie.getFreeSeats() + 1);
            movieDao.update(movie);
        }
    }

    private Ticket getTicket(int id) {
        Ticket ticket = ticketDao.getById(id);
        if (ticket == null) {
            throw new ServiceException("ticket with this id is not exists");
        }
        return ticket;
    }

    private Movie getMovie(int id) {
        Movie movie = movieDao.getById(id);
        if (movie == null) {
            throw new ServiceException("movie with this id is not exists");
        }
        return movie;
    }

    private void checkFreeSeats(Movie movie) {
        if (movie.getFreeSeats() == 0) {
            throw new ServiceException("no free seats");
        }
    }

}