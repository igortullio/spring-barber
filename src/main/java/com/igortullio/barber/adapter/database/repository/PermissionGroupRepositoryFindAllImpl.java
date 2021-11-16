package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PermissionGroupRepositoryFindAllImpl implements RepositoryFindAllPort<PermissionGroup> {

    private final PermissionGroupJpaRepository permissionGroupJpaRepository;
    private final ModelMapper modelMapper;

    public PermissionGroupRepositoryFindAllImpl(PermissionGroupJpaRepository permissionGroupJpaRepository,
                                                ModelMapper modelMapper) {
        this.permissionGroupJpaRepository = permissionGroupJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageBarber<PermissionGroup> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<PermissionGroupEntity> spec = (Specification<PermissionGroupEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<PermissionGroup> permissionGroupPage = permissionGroupJpaRepository.findAll(spec, pageRequest)
                .map(permissionGroupEntity -> modelMapper.map(permissionGroupEntity, PermissionGroup.class));

        return new PageBarber<>(permissionGroupPage.getContent(), PageablePortMapper.of(permissionGroupPage));
    }

}
