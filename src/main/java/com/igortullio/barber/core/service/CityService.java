package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.port.RepositoryPort;

public class CityService implements InterfaceService<City> {

    private final RepositoryPort<City> cityRepository;

    public CityService(RepositoryPort<City> cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City find(Long id) {
        return cityRepository.find(id);
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City update(Long id, City city) {
        return cityRepository.update(id, city);
    }

    @Override
    public void delete(Long id) {
        cityRepository.delete(id);
    }

}
