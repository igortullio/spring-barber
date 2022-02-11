package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.mapper.StateMapper;
import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class StateRepositoryFindAllImpl implements RepositoryFindAllPort<State> {

    private final StateJpaRepository stateJpaRepository;
    private final StateMapper stateMapper;

    public StateRepositoryFindAllImpl(StateJpaRepository stateJpaRepository,
                                      StateMapper stateMapper) {
        this.stateJpaRepository = stateJpaRepository;
        this.stateMapper = stateMapper;
    }

    @Override
    public PageBarber<State> findAll(Object specification, PageableBarber pageableBarber) {
        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<State> statePage = stateJpaRepository.findAll(pageRequest)
                .map(stateMapper::stateEntityToState);

        return new PageBarber<>(statePage.getContent(), PageablePortMapper.of(statePage));
    }

}
