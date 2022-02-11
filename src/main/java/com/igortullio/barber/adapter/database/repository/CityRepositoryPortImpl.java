package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import com.igortullio.barber.adapter.mapper.CityMapper;
import com.igortullio.barber.adapter.mapper.StateMapper;
import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.CityInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.CityNotFoundException;
import com.igortullio.barber.core.exception.not_found.StateNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class CityRepositoryPortImpl implements RepositoryPort<City> {

    private final CityJpaRepository cityJpaRepository;
    private final StateRepositoryPortImpl stateRepositoryPort;
    private final CityMapper cityMapper;
    private final StateMapper stateMapper;

    public CityRepositoryPortImpl(CityJpaRepository cityJpaRepository,
                                  StateRepositoryPortImpl stateRepositoryPort,
                                  CityMapper cityMapper,
                                  StateMapper stateMapper) {
        this.cityJpaRepository = cityJpaRepository;
        this.stateRepositoryPort = stateRepositoryPort;
        this.cityMapper = cityMapper;
        this.stateMapper = stateMapper;
    }

    @Override
    public City find(Long id) {
        CityEntity cityEntity = cityJpaRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        return cityMapper.cityEntityToCity(cityEntity);
    }

    @Override
    public City save(City city) {
        try {
            CityEntity cityEntity = cityMapper.cityToCityEntity(city);

            State state = stateRepositoryPort.find(city.getState().getId());
            cityEntity.setState(stateMapper.stateToStateEntity(state));

            existsCityWithNameInState(city, state);

            cityEntity = cityJpaRepository.save(cityEntity);
            return cityMapper.cityEntityToCity(cityEntity);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public City update(Long id, City city) {
        try {
            City cityInDB = find(id);
            cityInDB.setName(city.getName());

            State state = stateRepositoryPort.find(city.getState().getId());
            cityInDB.setState(state);

            existsCityWithNameInState(cityInDB, state);

            CityEntity cityEntity = cityMapper.cityToCityEntity(cityInDB);
            cityEntity = cityJpaRepository.save(cityEntity);

            return cityMapper.cityEntityToCity(cityEntity);
        } catch (StateNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            cityJpaRepository.deleteById(id);
            cityJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new CityInUseException(id);
        }
    }

    private City findByNameAndState(String name, StateEntity state) {
        CityEntity cityEntity = cityJpaRepository.findByNameAndState(name, state)
                .orElseThrow(() -> new CityNotFoundException(name));
        return cityMapper.cityEntityToCity(cityEntity);
    }

    private void existsCityWithNameInState(City city, State state) {
        try {
            StateEntity stateEntity = stateMapper.stateToStateEntity(state);
            city = findByNameAndState(city.getName(), stateEntity);
            throw new BarberException("City (" + city.getName() + ") already exists in the state (" + state.getName() + ")");
        } catch (CityNotFoundException ignored) { /* City not exists */ }
    }

}
