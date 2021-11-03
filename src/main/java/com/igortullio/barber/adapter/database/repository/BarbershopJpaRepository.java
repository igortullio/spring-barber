package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarbershopJpaRepository extends JpaRepository<BarbershopEntity, Long> {
}
