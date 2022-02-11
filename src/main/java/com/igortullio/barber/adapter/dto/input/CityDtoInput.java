package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityDtoInput extends AbstractDtoInput {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateDtoCityInput state;

    @Getter
    @Setter
    public static class StateDtoCityInput extends AbstractDtoInput {

    }

}
