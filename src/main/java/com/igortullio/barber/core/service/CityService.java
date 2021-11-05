package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import com.igortullio.barber.core.port.RepositoryPort;

public class CityService implements InterfaceService<City>, InterfaceFindAllService<City> {

    private final RepositoryPort<City> cityRepository;
    private final RepositoryFindAllPort<City> cityRepositoryFindAll;

    public CityService(RepositoryPort<City> cityRepository,
                       RepositoryFindAllPort<City> cityRepositoryFindAll) {
        this.cityRepository = cityRepository;
        this.cityRepositoryFindAll = cityRepositoryFindAll;
    }

    @Override
    public PageBarber<City> findAll(Object o, PageableBarber pageableBarber) {
        return cityRepositoryFindAll.findAll(o, pageableBarber);
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
