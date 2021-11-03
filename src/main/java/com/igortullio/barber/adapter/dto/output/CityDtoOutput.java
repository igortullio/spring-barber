package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CityDtoOutput extends AbstractDtoOutput {

    @NotBlank
    private String name;

    @NotBlank
    private StateDtoOutput state;

}
