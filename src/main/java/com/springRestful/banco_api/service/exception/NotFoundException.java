package com.springRestful.banco_api.service.exception;

public class NotFoundException extends BusinesException{

    public static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("Resource not found.");
    }
}
