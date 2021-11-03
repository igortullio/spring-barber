package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.PermissionGroup;

public class PermissionGroupNotFoundException extends AbstractNotFoundException {

    public PermissionGroupNotFoundException(Long id) {
        super(PermissionGroup.class, id);
    }

}
