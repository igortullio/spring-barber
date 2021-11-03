package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.PermissionGroupDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionGroupDtoOutput;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.service.PermissionGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
public class PermissionGroupController extends AbstractController<PermissionGroupDtoInput, PermissionGroupDtoOutput> {

    private final PermissionGroupService permissionGroupService;

    @Autowired
    public PermissionGroupController(ModelMapper modelMapper, PermissionGroupService permissionGroupService) {
        super(modelMapper);
        this.permissionGroupService = permissionGroupService;
    }

    @RolesAllowed({ PermissionGroupEntity.USER, PermissionGroupEntity.ADMIN })
    @Override
    public PermissionGroupDtoOutput get(@PathVariable Long id) {
        PermissionGroup permissionGroup = permissionGroupService.find(id);
        return modelMapper.map(permissionGroup, PermissionGroupDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public PermissionGroupDtoOutput post(@RequestBody @Valid PermissionGroupDtoInput permissionGroupDto) {
        PermissionGroup permissionGroup = modelMapper.map(permissionGroupDto, PermissionGroup.class);
        return modelMapper.map(permissionGroupService.save(permissionGroup), PermissionGroupDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public PermissionGroupDtoOutput put(@PathVariable Long id, @RequestBody @Valid PermissionGroupDtoInput permissionGroupDto) {
        PermissionGroup permissionGroup = modelMapper.map(permissionGroupDto, PermissionGroup.class);
        return modelMapper.map(permissionGroupService.update(id, permissionGroup), PermissionGroupDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public void delete(@PathVariable Long id) {
        permissionGroupService.delete(id);
    }

}
