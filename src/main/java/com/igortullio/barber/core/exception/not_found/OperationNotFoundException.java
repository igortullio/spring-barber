package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.Operation;

import java.time.DayOfWeek;

public class OperationNotFoundException extends AbstractNotFoundException {

    public OperationNotFoundException(Long id) {
        super(Operation.class, id);
    }

    public OperationNotFoundException(DayOfWeek day, String barbershopName) {
        super(Operation.class.getSimpleName() + "not found with day (" + day + ") and barbershop (" + barbershopName + ")");
    }

}
