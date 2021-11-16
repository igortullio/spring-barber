package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class PermissionRepositoryFindAllImpl implements RepositoryFindAllPort<Permission> {

    private final PermissionJpaRepository permissionJpaRepository;
    private final ModelMapper modelMapper;

    public PermissionRepositoryFindAllImpl(PermissionJpaRepository permissionJpaRepository,
                                           ModelMapper modelMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageBarber<Permission> findAll(Object specification, PageableBarber pageableBarber) {
        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<Permission> permissionPage = permissionJpaRepository.findAll(pageRequest)
                .map(permissionEntity -> modelMapper.map(permissionEntity, Permission.class));

        return new PageBarber<>(permissionPage.getContent(), PageablePortMapper.of(permissionPage));
    }

}
