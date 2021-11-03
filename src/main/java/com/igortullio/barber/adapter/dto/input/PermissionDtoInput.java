package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PermissionDtoInput extends AbstractDtoInput {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Valid
    @NotNull
    private PermissionGroupDtoCityInput permissionGroup;

    @Getter
    @Setter
    private static class PermissionGroupDtoCityInput extends AbstractDtoInput {

    }

}
