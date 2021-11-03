package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.Permission;

public class PermissionNotFoundException extends AbstractNotFoundException {

    public PermissionNotFoundException(Long id) {
        super(Permission.class, id);
    }

}
