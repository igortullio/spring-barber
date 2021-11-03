package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionGroupJpaRepository extends JpaRepository<PermissionGroupEntity, Long> {

}
