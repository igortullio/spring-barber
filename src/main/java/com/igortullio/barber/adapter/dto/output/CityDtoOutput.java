package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDtoOutput extends AbstractDtoOutput {

    private String name;
    private StateDtoOutput state;

}
