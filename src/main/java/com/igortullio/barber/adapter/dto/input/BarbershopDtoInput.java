package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BarbershopDtoInput extends AbstractDtoInput {

    @NotBlank
    private String name;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean open;

    @Valid
    @NotNull
    private AddressBarbershopDtoInput address;

    @Valid
    @NotNull
    private UserBarbershopDtoInput owner;

    @Getter
    @Setter
    private static class AddressBarbershopDtoInput extends AbstractDtoInput {

    }

    @Getter
    @Setter
    private static class UserBarbershopDtoInput extends AbstractDtoInput {

    }

}
