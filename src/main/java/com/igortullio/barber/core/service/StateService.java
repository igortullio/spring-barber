package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.port.RepositoryPort;

public class StateService implements InterfaceService<State> {

    private final RepositoryPort<State> stateRepository;

    public StateService(RepositoryPort<State> stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State find(Long id) {
        return stateRepository.find(id);
    }

    @Override
    public State save(State state) {
        return stateRepository.save(state);
    }

    @Override
    public State update(Long id, State state) {
        return stateRepository.update(id, state);
    }

    @Override
    public void delete(Long id) {
        stateRepository.delete(id);
    }

}
