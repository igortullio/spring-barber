package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.PermissionGroupDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionGroupDtoOutput;
import com.igortullio.barber.adapter.mapper.PermissionGroupMapper;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class PermissionGroupController implements InterfaceController<PermissionGroupDtoInput, PermissionGroupDtoOutput> {

    private final PermissionGroupService permissionGroupService;
    private final PermissionGroupMapper permissionGroupMapper;

    @Autowired
    public PermissionGroupController(PermissionGroupService permissionGroupService, PermissionGroupMapper permissionGroupMapper) {
        this.permissionGroupService = permissionGroupService;
        this.permissionGroupMapper = permissionGroupMapper;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PermissionGroupDtoOutput> getAll(@RequestParam(required = false) Long permissionId,
                                                 Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<PermissionGroupEntity> spec = SpecificationTemplate.permissionGroupPermissionId(permissionId);
        PageBarber<PermissionGroup> permissionGroupPageBarber = permissionGroupService.findAll(spec, pageableBarber);

        List<PermissionGroupDtoOutput> permissionGroupDtoOutputs = permissionGroupPageBarber.getList()
                .stream()
                .map(permissionGroupMapper::permissionGroupToPermissionGroupDtoOutput)
                .toList();

        return new PageImpl<>(permissionGroupDtoOutputs, pageable, permissionGroupPageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public PermissionGroupDtoOutput get(Long id) {
        PermissionGroup permissionGroup = permissionGroupService.find(id);
        return permissionGroupMapper.permissionGroupToPermissionGroupDtoOutput(permissionGroup);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public PermissionGroupDtoOutput post(PermissionGroupDtoInput permissionGroupDto) {
        PermissionGroup permissionGroup = permissionGroupMapper.permissionGroupDtoInputToPermissionGroup(permissionGroupDto);
        return permissionGroupMapper.permissionGroupToPermissionGroupDtoOutput(permissionGroupService.save(permissionGroup));
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public PermissionGroupDtoOutput put(Long id, PermissionGroupDtoInput permissionGroupDto) {
        PermissionGroup permissionGroup = permissionGroupMapper.permissionGroupDtoInputToPermissionGroup(permissionGroupDto);
        return permissionGroupMapper.permissionGroupToPermissionGroupDtoOutput(permissionGroupService.update(id, permissionGroup));
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public void delete(Long id) {
        permissionGroupService.delete(id);
    }

}
