package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.port.RepositoryPort;

public class PermissionGroupService implements InterfaceService<PermissionGroup> {

    private final RepositoryPort<PermissionGroup> permissionGroupRepository;

    public PermissionGroupService(RepositoryPort<PermissionGroup> permissionGroupRepository) {
        this.permissionGroupRepository = permissionGroupRepository;
    }

    @Override
    public PermissionGroup find(Long id) {
        return permissionGroupRepository.find(id);
    }

    @Override
    public PermissionGroup save(PermissionGroup permissionGroup) {
        return permissionGroupRepository.save(permissionGroup);
    }

    @Override
    public PermissionGroup update(Long id, PermissionGroup permissionGroup) {
        return permissionGroupRepository.update(id, permissionGroup);
    }

    @Override
    public void delete(Long id) {
        permissionGroupRepository.delete(id);
    }

}
