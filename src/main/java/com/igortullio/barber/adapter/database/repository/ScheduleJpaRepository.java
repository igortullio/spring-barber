package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long>, JpaSpecificationExecutor<ScheduleEntity> {
}
