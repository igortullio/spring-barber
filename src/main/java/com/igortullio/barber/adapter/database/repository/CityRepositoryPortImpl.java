package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.CityInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.CityNotFoundException;
import com.igortullio.barber.core.exception.not_found.StateNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class CityRepositoryPortImpl implements RepositoryPort<City> {

    private final CityJpaRepository cityJpaRepository;
    private final StateRepositoryPortImpl stateRepositoryPort;
    private final ModelMapper modelMapper;

    public CityRepositoryPortImpl(CityJpaRepository cityJpaRepository,
                                  StateRepositoryPortImpl stateRepositoryPort,
                                  ModelMapper modelMapper) {
        this.cityJpaRepository = cityJpaRepository;
        this.stateRepositoryPort = stateRepositoryPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public City find(Long id) {
        CityEntity cityEntity = cityJpaRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        return modelMapper.map(cityEntity, City.class);
    }

    @Override
    public City save(City city) {
        try {
            CityEntity cityEntity = modelMapper.map(city, CityEntity.class);

            State state = stateRepositoryPort.find(city.getState().getId());
            cityEntity.setState(modelMapper.map(state, StateEntity.class));

            existsCityWithNameInState(city.getName(), state);

            cityEntity = cityJpaRepository.save(cityEntity);
            return modelMapper.map(cityEntity, City.class);
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

            existsCityWithNameInState(cityInDB.getName(), state);

            CityEntity cityEntity = modelMapper.map(cityInDB, CityEntity.class);
            cityEntity = cityJpaRepository.save(cityEntity);

            return modelMapper.map(cityEntity, City.class);
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

    protected City findByNameAndState(String name, StateEntity state) {
        CityEntity cityEntity = cityJpaRepository.findByNameAndState(name, state)
                .orElseThrow(() -> new CityNotFoundException(name));
        return modelMapper.map(cityEntity, City.class);
    }

    private void existsCityWithNameInState(String name, State state) {
        try {
            StateEntity stateEntity = modelMapper.map(state, StateEntity.class);
            City city = findByNameAndState(name, stateEntity);
            throw new BarberException("City (" + city.getName() + ") already exists in the state (" + state.getName() + ")");
        } catch (CityNotFoundException ignored) { /* City not exists */ }
    }

}
