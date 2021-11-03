package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.Address;

public class AddressNotFoundException extends AbstractNotFoundException {

    public AddressNotFoundException(Long id) {
        super(Address.class, id);
    }

}
