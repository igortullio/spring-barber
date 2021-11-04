package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.Operation;

public class OperationInUseException extends AbstractInUseException {

    public OperationInUseException(Long id) {
        super(Operation.class, id);
    }

}
