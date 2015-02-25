package com.epam.theater.dao.mapper;

import com.epam.theater.common.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getString("ticket_id"));
        ticket.setSeatNumber(resultSet.getInt("seat_number"));

        MovieMapper movieMapper = new MovieMapper();
        ticket.setMovie(movieMapper.mapRow(resultSet, i));

        return ticket;
    }

}