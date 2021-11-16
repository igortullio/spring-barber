package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.exception.in_use.PermissionGroupInUseException;
import com.igortullio.barber.core.exception.not_found.PermissionGroupNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class PermissionGroupRepositoryPortImpl implements RepositoryPort<PermissionGroup> {

    private final PermissionGroupJpaRepository permissionGroupJpaRepository;
    private final ModelMapper modelMapper;

    public PermissionGroupRepositoryPortImpl(PermissionGroupJpaRepository permissionGroupJpaRepository,
                                             ModelMapper modelMapper) {
        this.permissionGroupJpaRepository = permissionGroupJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PermissionGroup find(Long id) {
        PermissionGroupEntity permissionGroupEntity = permissionGroupJpaRepository.findById(id)
                .orElseThrow(() -> new PermissionGroupNotFoundException(id));

        return modelMapper.map(permissionGroupEntity, PermissionGroup.class);
    }

    @Override
    public PermissionGroup save(PermissionGroup permissionGroup) {
        PermissionGroupEntity permissionGroupEntity = modelMapper.map(permissionGroup, PermissionGroupEntity.class);
        permissionGroupEntity.setName(permissionGroupEntity.getName());

        permissionGroupEntity = permissionGroupJpaRepository.save(permissionGroupEntity);
        return modelMapper.map(permissionGroupEntity, PermissionGroup.class);
    }

    @Override
    public PermissionGroup update(Long id, PermissionGroup permissionGroup) {
        PermissionGroup permissionGroupInDB = find(id);
        permissionGroupInDB.setName(permissionGroup.getName());

        PermissionGroupEntity permissionGroupEntity = modelMapper.map(permissionGroupInDB, PermissionGroupEntity.class);
        permissionGroupEntity = permissionGroupJpaRepository.save(permissionGroupEntity);

        return modelMapper.map(permissionGroupEntity, PermissionGroup.class);
    }

    @Override
    public void delete(Long id) {
        try {
            permissionGroupJpaRepository.deleteById(id);
            permissionGroupJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new PermissionGroupNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new PermissionGroupInUseException(id);
        }
    }

}
