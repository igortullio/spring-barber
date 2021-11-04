package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface OperationJpaRepository extends JpaRepository<OperationEntity, Long> {

    Optional<OperationEntity> findByDayAndBarbershop(DayOfWeek day, BarbershopEntity barbershop);

}
