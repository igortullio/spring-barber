package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.mapper.CityMapper;
import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CityRepositoryFindAllImpl implements RepositoryFindAllPort<City> {

    private final CityJpaRepository cityJpaRepository;
    private final CityMapper cityMapper;

    public CityRepositoryFindAllImpl(CityJpaRepository cityJpaRepository, CityMapper cityMapper) {
        this.cityJpaRepository = cityJpaRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public PageBarber<City> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<CityEntity> spec = (Specification<CityEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<City> cityPage = cityJpaRepository.findAll(spec, pageRequest)
                .map(cityMapper::cityEntityToCity);

        return new PageBarber<>(cityPage.getContent(), PageablePortMapper.of(cityPage));
    }

}
