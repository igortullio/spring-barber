package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.dto.input.UserDtoInput;
import com.igortullio.barber.adapter.dto.output.UserDtoOutput;
import com.igortullio.barber.core.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PermissionGroupMapper.class })
public interface UserMapper {

    UserDtoOutput userToUserDtoOutput(User user);

    User userDtoInputToUser(UserDtoInput userDtoInput);

    User userEntityToUser(UserEntity userEntity);

    UserEntity userToUserEntity(User user);

}
