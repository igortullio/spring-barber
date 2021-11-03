package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.exception.BarberException;

public abstract class AbstractNotFoundException extends BarberException {

    protected AbstractNotFoundException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " not found with id: " + id);
    }

    protected AbstractNotFoundException(String message) {
        super(message);
    }

}
