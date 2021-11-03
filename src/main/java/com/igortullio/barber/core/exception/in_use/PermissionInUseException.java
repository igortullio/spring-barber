package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.Permission;

public class PermissionInUseException extends AbstractInUseException {

    public PermissionInUseException(Long id) {
        super(Permission.class, id);
    }

}
