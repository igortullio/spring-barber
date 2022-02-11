package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.mapper.PermissionGroupMapper;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.exception.in_use.PermissionGroupInUseException;
import com.igortullio.barber.core.exception.not_found.PermissionGroupNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class PermissionGroupRepositoryPortImpl implements RepositoryPort<PermissionGroup> {

    private final PermissionGroupJpaRepository permissionGroupJpaRepository;
    private final PermissionGroupMapper permissionGroupMapper;

    public PermissionGroupRepositoryPortImpl(PermissionGroupJpaRepository permissionGroupJpaRepository,
                                             PermissionGroupMapper permissionGroupMapper) {
        this.permissionGroupJpaRepository = permissionGroupJpaRepository;
        this.permissionGroupMapper = permissionGroupMapper;
    }

    @Override
    public PermissionGroup find(Long id) {
        PermissionGroupEntity permissionGroupEntity = permissionGroupJpaRepository.findById(id)
                .orElseThrow(() -> new PermissionGroupNotFoundException(id));

        return permissionGroupMapper.permissionGroupEntityToPermissionGroup(permissionGroupEntity);
    }

    @Override
    public PermissionGroup save(PermissionGroup permissionGroup) {
        PermissionGroupEntity permissionGroupEntity = permissionGroupMapper.permissionGroupToPermissionGroupEntity(permissionGroup);
        permissionGroupEntity.setName(permissionGroupEntity.getName());

        permissionGroupEntity = permissionGroupJpaRepository.save(permissionGroupEntity);
        return permissionGroupMapper.permissionGroupEntityToPermissionGroup(permissionGroupEntity);
    }

    @Override
    public PermissionGroup update(Long id, PermissionGroup permissionGroup) {
        PermissionGroup permissionGroupInDB = find(id);
        permissionGroupInDB.setName(permissionGroup.getName());

        PermissionGroupEntity permissionGroupEntity = permissionGroupMapper.permissionGroupToPermissionGroupEntity(permissionGroupInDB);
        permissionGroupEntity = permissionGroupJpaRepository.save(permissionGroupEntity);

        return permissionGroupMapper.permissionGroupEntityToPermissionGroup(permissionGroupEntity);
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
