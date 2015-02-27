package com.epam.theater.service.message;

public class FieldStatusMessage extends StatusMessage {

    private String field;
    private String rejectedValue;

    public FieldStatusMessage(Status status, String message, String field, String rejectedValue) {
        super(status, message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

}