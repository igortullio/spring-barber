package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityJpaRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findByNameAndState(String name, StateEntity state);

}
