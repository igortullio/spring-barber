package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.City;

public class CityNotFoundException extends AbstractNotFoundException {

    public CityNotFoundException(Long id) {
        super(City.class, id);
    }

    public CityNotFoundException(String name) {
        super(City.class.getSimpleName() + " not found with name: " + name);
    }

}
