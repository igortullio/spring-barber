package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.port.RepositoryPort;

public class PermissionService implements InterfaceService<Permission> {

    private final RepositoryPort<Permission> permissionRepository;

    public PermissionService(RepositoryPort<Permission> permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission find(Long id) {
        return permissionRepository.find(id);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Long id, Permission permission) {
        return permissionRepository.update(id, permission);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.delete(id);
    }

}
