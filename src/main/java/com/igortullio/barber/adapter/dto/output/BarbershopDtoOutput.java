package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarbershopDtoOutput extends AbstractDtoOutput {

    private String name;
    private Boolean active;
    private Boolean open;
    private AddressDtoOutput address;
    private UserDtoOutput owner;

}
