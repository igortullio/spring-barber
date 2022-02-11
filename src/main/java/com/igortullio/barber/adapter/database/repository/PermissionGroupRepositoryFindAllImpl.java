package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.mapper.PermissionGroupMapper;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PermissionGroupRepositoryFindAllImpl implements RepositoryFindAllPort<PermissionGroup> {

    private final PermissionGroupJpaRepository permissionGroupJpaRepository;
    private final PermissionGroupMapper permissionGroupMapper;

    public PermissionGroupRepositoryFindAllImpl(PermissionGroupJpaRepository permissionGroupJpaRepository,
                                                PermissionGroupMapper permissionGroupMapper) {
        this.permissionGroupJpaRepository = permissionGroupJpaRepository;
        this.permissionGroupMapper = permissionGroupMapper;
    }

    @Override
    public PageBarber<PermissionGroup> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<PermissionGroupEntity> spec = (Specification<PermissionGroupEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<PermissionGroup> permissionGroupPage = permissionGroupJpaRepository.findAll(spec, pageRequest)
                .map(permissionGroupMapper::permissionGroupEntityToPermissionGroup);

        return new PageBarber<>(permissionGroupPage.getContent(), PageablePortMapper.of(permissionGroupPage));
    }

}
