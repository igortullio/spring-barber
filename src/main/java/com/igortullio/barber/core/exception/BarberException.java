package com.igortullio.barber.core.exception;

public class BarberException extends RuntimeException {

    public BarberException(String message) {
        super(message);
    }

    public BarberException(String message, Throwable cause) {
        super(message, cause);
    }

}
