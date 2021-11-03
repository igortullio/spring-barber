package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {
}
