package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.PermissionGroupDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionGroupDtoOutput;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.service.PermissionGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/groups")
public class PermissionGroupController extends AbstractController<PermissionGroupDtoInput, PermissionGroupDtoOutput> {

    private final PermissionGroupService permissionGroupService;

    @Autowired
    public PermissionGroupController(ModelMapper modelMapper, PermissionGroupService permissionGroupService) {
        super(modelMapper);
        this.permissionGroupService = permissionGroupService;
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public PermissionGroupDtoOutput get(Long id) {
        PermissionGroup permissionGroup = permissionGroupService.find(id);
        return modelMapper.map(permissionGroup, PermissionGroupDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public PermissionGroupDtoOutput post(PermissionGroupDtoInput permissionGroupDto) {
        PermissionGroup permissionGroup = modelMapper.map(permissionGroupDto, PermissionGroup.class);
        return modelMapper.map(permissionGroupService.save(permissionGroup), PermissionGroupDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public PermissionGroupDtoOutput put(Long id, PermissionGroupDtoInput permissionGroupDto) {
        PermissionGroup permissionGroup = modelMapper.map(permissionGroupDto, PermissionGroup.class);
        return modelMapper.map(permissionGroupService.update(id, permissionGroup), PermissionGroupDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public void delete(Long id) {
        permissionGroupService.delete(id);
    }

}
