package com.epam.theater.service;

import org.openspaces.remoting.RemotingService;

import java.util.logging.Logger;

@RemotingService
public class EventServiceImpl implements EventService {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void logToConsole() {
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("logToConsole");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

}