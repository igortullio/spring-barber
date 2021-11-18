package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.PermissionDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionDtoOutput;
import com.igortullio.barber.core.domain.Permission;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.PermissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends AbstractController<PermissionDtoInput, PermissionDtoOutput> {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(ModelMapper modelMapper, PermissionService permissionService) {
        super(modelMapper);
        this.permissionService = permissionService;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PermissionDtoOutput> getAll(Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        PageBarber<Permission> permissionPageBarber = permissionService.findAll(null, pageableBarber);

        List<PermissionDtoOutput> permissionDtoOutputs = permissionPageBarber.getList()
                .stream()
                .map(permission -> modelMapper.map(permission, PermissionDtoOutput.class))
                .toList();

        return new PageImpl<>(permissionDtoOutputs, pageable, permissionPageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public PermissionDtoOutput get(Long id) {
        Permission permission = permissionService.find(id);
        return modelMapper.map(permission, PermissionDtoOutput.class);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public PermissionDtoOutput post(PermissionDtoInput permissionDto) {
        Permission permission = modelMapper.map(permissionDto, Permission.class);
        return modelMapper.map(permissionService.save(permission), PermissionDtoOutput.class);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public PermissionDtoOutput put(Long id, PermissionDtoInput permissionDto) {
        Permission permission = modelMapper.map(permissionDto, Permission.class);
        return modelMapper.map(permissionService.update(id, permission), PermissionDtoOutput.class);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public void delete(Long id) {
        permissionService.delete(id);
    }

}
