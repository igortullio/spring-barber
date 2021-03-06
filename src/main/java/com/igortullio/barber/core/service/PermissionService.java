package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import com.igortullio.barber.core.port.RepositoryPort;

public class PermissionService implements InterfaceService<Permission>, InterfaceFindAllService<Permission> {

    private final RepositoryPort<Permission> permissionRepository;
    private final RepositoryFindAllPort<Permission> permissionRepositoryFindAll;

    public PermissionService(RepositoryPort<Permission> permissionRepository,
                             RepositoryFindAllPort<Permission> permissionRepositoryFindAll) {
        this.permissionRepository = permissionRepository;
        this.permissionRepositoryFindAll = permissionRepositoryFindAll;
    }

    @Override
    public PageBarber<Permission> findAll(Object o, PageableBarber pageableBarber) {
        return permissionRepositoryFindAll.findAll(o, pageableBarber);
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
