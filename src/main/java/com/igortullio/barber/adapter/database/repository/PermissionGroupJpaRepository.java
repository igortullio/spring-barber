package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionGroupJpaRepository extends JpaRepository<PermissionGroupEntity, Long>, JpaSpecificationExecutor<PermissionGroupEntity> {
}
