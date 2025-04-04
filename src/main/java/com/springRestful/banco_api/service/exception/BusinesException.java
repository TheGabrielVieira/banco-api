package com.springRestful.banco_api.service.exception;

public class BusinesException extends RuntimeException{

    public static final long serialVersionUID = 1L;

    public BusinesException(String message){
        super(message);
    }
}
