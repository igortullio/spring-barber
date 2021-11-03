package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.PermissionGroup;

public class PermissionGroupInUseException extends AbstractInUseException {

    public PermissionGroupInUseException(Long id) {
        super(PermissionGroup.class, id);
    }

}
