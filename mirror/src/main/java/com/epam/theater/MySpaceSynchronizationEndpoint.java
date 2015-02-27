package com.epam.theater;

import com.epam.theater.common.Movie;
import com.epam.theater.common.Ticket;
import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.sync.DataSyncOperation;
import com.gigaspaces.sync.OperationsBatchData;
import com.gigaspaces.sync.SpaceSynchronizationEndpoint;

import java.util.logging.Logger;

public class MySpaceSynchronizationEndpoint extends SpaceSynchronizationEndpoint {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void onOperationsBatchSynchronization(OperationsBatchData batchData) {
        DataSyncOperation[] operations = batchData.getBatchDataItems();

        for (DataSyncOperation operation : operations) {
            log.info("Mirror: " + operation.getDataSyncOperationType() + " operation!");

            if (operation.supportsDataAsObject()) {
                Object pojo = operation.getDataAsObject();
                if (pojo instanceof Movie) {
                    log.info("Mirror: " + (Movie) pojo);
                }
                if (pojo instanceof Ticket) {
                    log.info("Mirror: " + (Ticket) pojo);
                }
            } else if (operation.supportsDataAsDocument()) {
                SpaceDocument document = operation.getDataAsDocument();
                log.info("Mirror: space document = " + document.getTypeName());
            }
            log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        }
    }

}