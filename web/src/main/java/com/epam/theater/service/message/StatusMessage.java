package com.epam.theater.service.message;

public class StatusMessage {

    public static enum Status {
        SUCCESS, ERROR
    }

    private Status status;
    private String message;

    public StatusMessage() {}

    public StatusMessage(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}