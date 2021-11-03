package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.City;

public class CityInUseException extends AbstractInUseException {

    public CityInUseException(Long id) {
        super(City.class, id);
    }

}
