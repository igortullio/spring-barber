package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import com.igortullio.barber.core.port.RepositoryPort;

public class PermissionGroupService implements InterfaceService<PermissionGroup>, InterfaceFindAllService<PermissionGroup> {

    private final RepositoryPort<PermissionGroup> permissionGroupRepository;
    private final RepositoryFindAllPort<PermissionGroup> permissionGroupRepositoryFindAll;

    public PermissionGroupService(RepositoryPort<PermissionGroup> permissionGroupRepository,
                                  RepositoryFindAllPort<PermissionGroup> permissionGroupRepositoryFindAll) {
        this.permissionGroupRepository = permissionGroupRepository;
        this.permissionGroupRepositoryFindAll = permissionGroupRepositoryFindAll;
    }

    @Override
    public PageBarber<PermissionGroup> findAll(Object o, PageableBarber pageableBarber) {
        return permissionGroupRepositoryFindAll.findAll(o, pageableBarber);
    }

    @Override
    public PermissionGroup find(Long id) {
        return permissionGroupRepository.find(id);
    }

    @Override
    public PermissionGroup save(PermissionGroup permissionGroup) {
        verify(permissionGroup);
        return permissionGroupRepository.save(permissionGroup);
    }

    @Override
    public PermissionGroup update(Long id, PermissionGroup permissionGroup) {
        verify(permissionGroup);
        return permissionGroupRepository.update(id, permissionGroup);
    }

    @Override
    public void delete(Long id) {
        permissionGroupRepository.delete(id);
    }

    private void verify(PermissionGroup permissionGroup) {
        verifyNameUpperCase(permissionGroup);
        verifyNameWithoutSpaces(permissionGroup);
    }

    private void verifyNameUpperCase(PermissionGroup permissionGroup) {
        permissionGroup.setName(permissionGroup.getName().toUpperCase());
    }

    private void verifyNameWithoutSpaces(PermissionGroup permissionGroup) {
        permissionGroup.setName(permissionGroup.getName().replace(" ", "_"));
    }

}
