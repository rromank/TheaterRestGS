package com.epam.theater.controller;

import com.epam.theater.service.exception.ServiceException;
import com.epam.theater.service.message.FieldStatusMessage;
import com.epam.theater.service.message.StatusMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected @ResponseBody
    ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldStatusMessage> exceptionMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            exceptionMessages.add(getExceptionMessage(fieldError));
        }
        return new ResponseEntity<Object>(exceptionMessages, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StatusMessage statusMessage = new StatusMessage(StatusMessage.Status.ERROR, ex.getMessage());
        return new ResponseEntity<Object>(statusMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleServiceException(ServiceException ex) {
        StatusMessage statusMessage = new StatusMessage(StatusMessage.Status.ERROR, ex.getMessage());
        return new ResponseEntity<Object>(statusMessage, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/error")
    public @ResponseBody StatusMessage error() {
        return new StatusMessage(StatusMessage.Status.ERROR, "not supported");
    }

    private FieldStatusMessage getExceptionMessage(FieldError fieldError) {
        String code = fieldError.getCode();
        String field = fieldError.getField();
        String rejectedValue = String.valueOf(fieldError.getRejectedValue());
        return new FieldStatusMessage(StatusMessage.Status.ERROR, code, field, rejectedValue);
    }

}