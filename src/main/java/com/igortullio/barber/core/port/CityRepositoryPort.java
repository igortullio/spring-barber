package com.igortullio.barber.core.port;

import com.igortullio.barber.core.domain.City;

public interface CityRepositoryPort {

    City findByNameAndState(City city);
    
}
