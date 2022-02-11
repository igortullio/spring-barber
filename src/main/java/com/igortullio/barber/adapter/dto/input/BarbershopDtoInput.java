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

    @Valid
    @NotNull
    private AddressBarbershopDtoInput address;

    @Getter
    @Setter
    public static class AddressBarbershopDtoInput extends AbstractDtoInput {

    }

}
