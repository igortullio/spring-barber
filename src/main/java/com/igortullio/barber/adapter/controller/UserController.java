package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.UserDtoInput;
import com.igortullio.barber.adapter.dto.output.UserDtoOutput;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserDtoInput, UserDtoOutput> {

    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        super(modelMapper);
        this.userService = userService;
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public UserDtoOutput get(@PathVariable Long id) {
        User user = userService.find(id);
        return modelMapper.map(user, UserDtoOutput.class);
    }

    @Override
    public UserDtoOutput post(@RequestBody @Valid UserDtoInput userDto) {
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userService.save(user), UserDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public UserDtoOutput put(@PathVariable Long id, @RequestBody @Valid UserDtoInput userDto) {
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userService.update(id, user), UserDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
