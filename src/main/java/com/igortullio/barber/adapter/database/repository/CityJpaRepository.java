package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CityJpaRepository extends JpaRepository<CityEntity, Long>, JpaSpecificationExecutor<CityEntity> {

    Optional<CityEntity> findByNameAndState(String name, StateEntity state);

}
