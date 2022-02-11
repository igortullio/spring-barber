package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.PermissionDtoInput;
import com.igortullio.barber.adapter.dto.input.PermissionGroupDtoInput;
import com.igortullio.barber.adapter.dto.input.UserDtoInput;
import com.igortullio.barber.adapter.dto.output.PermissionGroupDtoOutput;
import com.igortullio.barber.core.domain.PermissionGroup;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PermissionGroupMapper {

    PermissionGroupDtoOutput permissionGroupToPermissionGroupDtoOutput(PermissionGroup permissionGroup);

    PermissionGroup permissionGroupDtoInputToPermissionGroup(PermissionGroupDtoInput permissionGroupDtoInput);

    PermissionGroup permissionGroupDtoCityInputToPermissionGroup(PermissionDtoInput.PermissionGroupDtoCityInput permissionGroupDtoCityInput);

    Set<PermissionGroup> permissionGroupUserDtoInputSetToPermissionGroupSet(Set<UserDtoInput.PermissionGroupUserDtoInput> permissionGroupUserDtoInputSet);

    PermissionGroupEntity permissionGroupToPermissionGroupEntity(PermissionGroup permissionGroup);

    PermissionGroup permissionGroupEntityToPermissionGroup(PermissionGroupEntity permissionGroupEntity);

}
