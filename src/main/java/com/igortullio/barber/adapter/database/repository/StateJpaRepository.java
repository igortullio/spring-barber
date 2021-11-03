package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateJpaRepository extends JpaRepository<StateEntity, Long> {
}
