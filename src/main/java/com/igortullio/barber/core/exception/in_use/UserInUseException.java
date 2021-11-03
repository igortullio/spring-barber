package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.User;

public class UserInUseException extends AbstractInUseException {

    public UserInUseException(Long id) {
        super(User.class, id);
    }

}
