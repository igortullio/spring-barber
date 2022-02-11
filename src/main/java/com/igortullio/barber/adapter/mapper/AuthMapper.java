package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.dto.output.AuthDtoOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, UserMapper.class })
public interface AuthMapper {

    AuthDtoOutput userEntityToAuthDtoOutput(UserEntity userEntity);

}
