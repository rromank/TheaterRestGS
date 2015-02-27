package com.epam.theater.controller;

import com.epam.theater.common.Ticket;
import com.epam.theater.service.TicketService;
import com.epam.theater.service.message.StatusMessage;
import org.openspaces.remoting.ExecutorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @ExecutorProxy
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Ticket> getAll() {
        return ticketService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public StatusMessage delete(@PathVariable(value = "id") int id) {
        boolean isDeleted = ticketService.delete(id);
        StatusMessage statusMessage = new StatusMessage();
        if (isDeleted) {
            statusMessage.setStatus(StatusMessage.Status.SUCCESS);
        } else {
            statusMessage.setStatus(StatusMessage.Status.ERROR);
        }
        return statusMessage;
    }

    @RequestMapping(value = "/buy/{movie_id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> buy(@PathVariable(value = "movie_id") int movieId) {
        Ticket ticket = ticketService.buy(movieId);
        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
    }

}