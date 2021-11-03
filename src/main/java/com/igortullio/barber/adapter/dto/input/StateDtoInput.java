package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateDtoInput extends AbstractDtoInput {

    @NotBlank
    private String name;

    @NotBlank
    private String initials;

}
