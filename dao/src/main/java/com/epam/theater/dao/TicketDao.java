package com.epam.theater.dao;

import com.epam.theater.common.Ticket;

import java.util.List;

public interface TicketDao {

    public Ticket add(Ticket ticket);

    public List<Ticket> getAll();

    public boolean delete(int id);

    public Ticket getById(int id);

    public int getUnusedSeatNumber(int movieId);

    public boolean deleteAll();

}