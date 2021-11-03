package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.User;

public class UserNotFoundException extends AbstractNotFoundException {

    public UserNotFoundException(Long id) {
        super(User.class, id);
    }

    public UserNotFoundException(String email) {
        super(User.class.getSimpleName() + " not found with email: " + email);
    }

}
