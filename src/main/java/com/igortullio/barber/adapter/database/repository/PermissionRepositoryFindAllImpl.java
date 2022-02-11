package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.mapper.PermissionMapper;
import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class PermissionRepositoryFindAllImpl implements RepositoryFindAllPort<Permission> {

    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionMapper permissionMapper;

    public PermissionRepositoryFindAllImpl(PermissionJpaRepository permissionJpaRepository,
                                           PermissionMapper permissionMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PageBarber<Permission> findAll(Object specification, PageableBarber pageableBarber) {
        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<Permission> permissionPage = permissionJpaRepository.findAll(pageRequest)
                .map(permissionMapper::permissionEntityToPermission);

        return new PageBarber<>(permissionPage.getContent(), PageablePortMapper.of(permissionPage));
    }

}
