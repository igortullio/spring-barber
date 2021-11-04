package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class UserDtoInput extends AbstractDtoInput {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    @Valid
    private Set<PermissionGroupUserDtoInput> permissionGroupSet;

    @Getter
    @Setter
    private static class PermissionGroupUserDtoInput extends AbstractDtoInput {

    }

}
