package com.epam.theater.listener;

import com.epam.theater.common.Ticket;
import com.gigaspaces.client.ChangeSet;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.NotifyType;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.MultiTakeReceiveOperationHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@Polling
@EventDriven
@NotifyType(write = true)
public class TicketListener {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    GigaSpace gigaSpace;

    @ReceiveHandler
    ReceiveOperationHandler receiveHandler() {
        MultiTakeReceiveOperationHandler receiveHandler = new MultiTakeReceiveOperationHandler();
        receiveHandler.setMaxEntries(100);
        return receiveHandler;
    }

    @EventTemplate
    private Ticket newTicket() {
        Ticket template = new Ticket();
        template.setChecked(false);
        return template;
    }

    @SpaceDataEvent
    public Ticket ticketWasBought(Ticket ticket) {
        log.info("=====================================================================");
        log.info("Ticket with seat number " + ticket.getSeatNumber() + " was bought: " + ticket.toString());
        log.info("=====================================================================");
        ticket.setChecked(true);
        return ticket;
    }

}