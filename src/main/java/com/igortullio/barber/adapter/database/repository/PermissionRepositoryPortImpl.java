package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionEntity;
import com.igortullio.barber.adapter.mapper.PermissionGroupMapper;
import com.igortullio.barber.adapter.mapper.PermissionMapper;
import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.PermissionInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.PermissionNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class PermissionRepositoryPortImpl implements RepositoryPort<Permission> {

    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort;
    private final PermissionMapper permissionMapper;
    private final PermissionGroupMapper permissionGroupMapper;

    public PermissionRepositoryPortImpl(PermissionJpaRepository permissionJpaRepository,
                                        PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                        PermissionMapper permissionMapper,
                                        PermissionGroupMapper permissionGroupMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.permissionGroupRepositoryPort = permissionGroupRepositoryPort;
        this.permissionMapper = permissionMapper;
        this.permissionGroupMapper = permissionGroupMapper;
    }

    @Override
    public Permission find(Long id) {
        PermissionEntity permissionEntity = permissionJpaRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));

        return permissionMapper.permissionEntityToPermission(permissionEntity);
    }

    @Override
    public Permission save(Permission permission) {
        try {
            PermissionEntity permissionEntity = permissionMapper.permissionToPermissionEntity(permission);
            permissionEntity.setName(permissionEntity.getName().toUpperCase());

            PermissionGroup permissionGroup = permissionGroupRepositoryPort.find(permission.getPermissionGroup().getId());
            permissionEntity.setPermissionGroup(permissionGroupMapper.permissionGroupToPermissionGroupEntity(permissionGroup));

            permissionEntity = permissionJpaRepository.save(permissionEntity);
            return permissionMapper.permissionEntityToPermission(permissionEntity);
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

            PermissionEntity permissionEntity = permissionMapper.permissionToPermissionEntity(permissionInDB);
            permissionEntity = permissionJpaRepository.save(permissionEntity);

            return permissionMapper.permissionEntityToPermission(permissionEntity);
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
