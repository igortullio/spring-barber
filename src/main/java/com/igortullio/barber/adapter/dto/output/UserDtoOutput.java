package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoOutput extends AbstractDtoOutput {

    private String name;
    private String email;
    private String phone;

}
