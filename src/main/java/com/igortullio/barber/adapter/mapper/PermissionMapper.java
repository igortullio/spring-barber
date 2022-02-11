package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.PermissionEntity;
import com.igortullio.barber.adapter.dto.input.PermissionDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionDtoOutput;
import com.igortullio.barber.core.domain.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PermissionGroupMapper.class })
public interface PermissionMapper {

    PermissionDtoOutput permissionToPermissionDtoOutput(Permission permission);

    Permission permissionDtoInputToPermission(PermissionDtoInput permissionDtoInput);

    PermissionEntity permissionToPermissionEntity(Permission permission);

    Permission permissionEntityToPermission(PermissionEntity permissionEntity);

}
