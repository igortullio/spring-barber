package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
}
