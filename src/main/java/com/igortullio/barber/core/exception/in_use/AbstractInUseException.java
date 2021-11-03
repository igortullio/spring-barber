package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.exception.BarberException;

public abstract class AbstractInUseException extends BarberException {

    protected AbstractInUseException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " with id (" + id + ") is in use");
    }

}
