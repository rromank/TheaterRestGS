package com.epam.theater.dao;

import com.epam.theater.common.Movie;
import com.epam.theater.common.Ticket;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GSTicketDao implements TicketDao {

    @Autowired
    GigaSpace space;

    @Override
    public Ticket add(Ticket ticket) {
        space.write(ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getAll() {
        Ticket[] tickets = space.readMultiple(new Ticket());
        return Arrays.asList(tickets);
    }

    @Override
    public boolean delete(int id) {
        Ticket template = new Ticket();
        template.setId(String.valueOf(id));
        return space.clear(template) > 0;
    }

    @Override
    public Ticket getById(int id) {
        return space.readById(Ticket.class, id);
    }

    private Movie getMovieById(int id) {
        return space.readById(Movie.class, id);
    }

    @Override
    public int getUnusedSeatNumber(int movieId) {
        SQLQuery<Ticket> query = new SQLQuery<Ticket>(Ticket.class, "movie.id = ?");
        query.setParameter(1, movieId);
        Ticket[] tickets = space.readMultiple(query);
        return getUnusedSeatNumber(movieId, tickets);
    }

    private int getUnusedSeatNumber(int movieId, Ticket[] tickets) {
        Movie movie = getMovieById(movieId);
        sortTickets(tickets);
        for (int i = 0; i < movie.getTotalSeats(); i++) {
            if (isUnusedSeat(i, tickets)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isUnusedSeat(int seatNumber, Ticket[] tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getSeatNumber() == seatNumber) {
                return false;
            }
        }
        return true;
    }

    private void sortTickets(Ticket[] tickets) {
        Arrays.sort(tickets, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return o1.getSeatNumber() - o2.getSeatNumber();
            }
        });
    }

    @Override
    public boolean deleteAll() {
        return space.clear(new Ticket()) > 0;
    }

}