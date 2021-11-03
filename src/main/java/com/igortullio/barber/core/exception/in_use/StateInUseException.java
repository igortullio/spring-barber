package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.State;

public class StateInUseException extends AbstractInUseException {

    public StateInUseException(Long id) {
        super(State.class, id);
    }

}
