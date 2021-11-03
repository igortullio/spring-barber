package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.Barbershop;

public class BarbershopNotFoundException extends AbstractNotFoundException {

    public BarbershopNotFoundException(Long id) {
        super(Barbershop.class, id);
    }

}
