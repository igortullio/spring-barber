package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.core.exception.not_found.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email) throws UserNotFoundException;

}
