package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractRepository {

    public static UserEntity getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

}
