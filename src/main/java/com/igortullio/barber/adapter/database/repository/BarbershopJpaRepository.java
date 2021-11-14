package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BarbershopJpaRepository extends JpaRepository<BarbershopEntity, Long>, JpaSpecificationExecutor<BarbershopEntity> {
}
