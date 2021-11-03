package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.Address;

public class AddressInUseException extends AbstractInUseException {

    public AddressInUseException(Long id) {
        super(Address.class, id);
    }

}
