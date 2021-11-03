package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.State;

public class StateNotFoundException extends AbstractNotFoundException {

    public StateNotFoundException(Long id) {
        super(State.class, id);
    }

}
