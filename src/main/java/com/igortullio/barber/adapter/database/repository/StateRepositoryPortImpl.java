package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.StateEntity;
import com.igortullio.barber.adapter.mapper.StateMapper;
import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.exception.in_use.StateInUseException;
import com.igortullio.barber.core.exception.not_found.StateNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class StateRepositoryPortImpl implements RepositoryPort<State> {

    private final StateJpaRepository stateJpaRepository;
    private final StateMapper stateMapper;

    public StateRepositoryPortImpl(StateJpaRepository stateJpaRepository,
                                   StateMapper stateMapper) {
        this.stateJpaRepository = stateJpaRepository;
        this.stateMapper = stateMapper;
    }

    @Override
    public State find(Long id) {
        StateEntity stateEntity = stateJpaRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));

        return stateMapper.stateEntityToState(stateEntity);
    }

    @Override
    public State save(State state) {
        StateEntity stateEntity = stateMapper.stateToStateEntity(state);
        stateEntity = stateJpaRepository.save(stateEntity);

        return stateMapper.stateEntityToState(stateEntity);
    }

    @Override
    public State update(Long id, State state) {
        State stateInDB = find(id);
        stateInDB.setName(state.getName());
        stateInDB.setInitials(state.getInitials());

        StateEntity stateEntity = stateMapper.stateToStateEntity(stateInDB);
        stateEntity = stateJpaRepository.save(stateEntity);

        return stateMapper.stateEntityToState(stateEntity);
    }

    @Override
    public void delete(Long id) {
        try {
            stateJpaRepository.deleteById(id);
            stateJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new StateNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new StateInUseException(id);
        }
    }

}
