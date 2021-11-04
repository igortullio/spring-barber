package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionEntity;
import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.PermissionInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.PermissionNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class PermissionRepositoryPortImpl implements RepositoryPort<Permission> {

    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort;
    private final ModelMapper modelMapper;

    public PermissionRepositoryPortImpl(PermissionJpaRepository permissionJpaRepository,
                                        PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                        ModelMapper modelMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.permissionGroupRepositoryPort = permissionGroupRepositoryPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public Permission find(Long id) {
        PermissionEntity permissionEntity = permissionJpaRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));

        return modelMapper.map(permissionEntity, Permission.class);
    }

    @Override
    public Permission save(Permission permission) {
        try {
            PermissionEntity permissionEntity = modelMapper.map(permission, PermissionEntity.class);
            permissionEntity.setName(permissionEntity.getName().toUpperCase());

            PermissionGroup permissionGroup = permissionGroupRepositoryPort.find(permission.getPermissionGroup().getId());
            permissionEntity.setPermissionGroup(modelMapper.map(permissionGroup, PermissionGroupEntity.class));

            permissionEntity = permissionJpaRepository.save(permissionEntity);
            return modelMapper.map(permissionEntity, Permission.class);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public Permission update(Long id, Permission permission) {
        try {
            Permission permissionInDB = find(id);
            permissionInDB.setName(permission.getName().toUpperCase());
            permissionInDB.setDescription(permission.getDescription());

            PermissionGroup permissionGroup = permissionGroupRepositoryPort.find(permission.getPermissionGroup().getId());
            permissionInDB.setPermissionGroup(permissionGroup);

            PermissionEntity permissionEntity = modelMapper.map(permissionInDB, PermissionEntity.class);
            permissionEntity = permissionJpaRepository.save(permissionEntity);

            return modelMapper.map(permissionEntity, Permission.class);
        } catch (PermissionNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            permissionJpaRepository.deleteById(id);
            permissionJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new PermissionNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new PermissionInUseException(id);
        }
    }

}
