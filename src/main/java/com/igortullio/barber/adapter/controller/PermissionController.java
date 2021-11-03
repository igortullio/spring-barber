package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.PermissionDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionDtoOutput;
import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.service.PermissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends AbstractController<PermissionDtoInput, PermissionDtoOutput> {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(ModelMapper modelMapper, PermissionService permissionService) {
        super(modelMapper);
        this.permissionService = permissionService;
    }

    @RolesAllowed({ PermissionGroupEntity.USER, PermissionGroupEntity.ADMIN })
    @Override
    public PermissionDtoOutput get(@PathVariable Long id) {
        Permission permission = permissionService.find(id);
        return modelMapper.map(permission, PermissionDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public PermissionDtoOutput post(@RequestBody @Valid PermissionDtoInput permissionDto) {
        Permission permission = modelMapper.map(permissionDto, Permission.class);
        return modelMapper.map(permissionService.save(permission), PermissionDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public PermissionDtoOutput put(@PathVariable Long id, @RequestBody @Valid PermissionDtoInput permissionDto) {
        Permission permission = modelMapper.map(permissionDto, Permission.class);
        return modelMapper.map(permissionService.update(id, permission), PermissionDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }

}
