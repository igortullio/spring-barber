package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.Barbershop;

public class BarbershopInUseException extends AbstractInUseException {

    public BarbershopInUseException(Long id) {
        super(Barbershop.class, id);
    }

}
