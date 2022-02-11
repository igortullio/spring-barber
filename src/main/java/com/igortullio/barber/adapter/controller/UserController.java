package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.dto.input.UserDtoInput;
import com.igortullio.barber.adapter.dto.output.UserDtoOutput;
import com.igortullio.barber.adapter.mapper.UserMapper;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/users")
public class UserController implements InterfaceController<UserDtoInput, UserDtoOutput> {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RolesAllowed({ PermissionGroup.ADMIN })
    @Override
    public UserDtoOutput get(Long id) {
        User user = userService.find(id);
        return userMapper.userToUserDtoOutput(user);
    }

    @Override
    public UserDtoOutput post(UserDtoInput userDto) {
        User user = userMapper.userDtoInputToUser(userDto);
        return userMapper.userToUserDtoOutput(userService.save(user));
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public UserDtoOutput put(Long id, UserDtoInput userDto) {
        User user = userMapper.userDtoInputToUser(userDto);
        return userMapper.userToUserDtoOutput(userService.update(id, user));
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

}
