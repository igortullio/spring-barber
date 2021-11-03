package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ScheduleDtoOutput extends AbstractDtoOutput {

    private OffsetDateTime date;
    private String status;
    private BarbershopDtoOutput barbershop;
    private UserDtoOutput user;

}
