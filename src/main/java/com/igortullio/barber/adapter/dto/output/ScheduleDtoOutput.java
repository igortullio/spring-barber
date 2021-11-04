package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ScheduleDtoOutput extends AbstractDtoOutput {

    private LocalTime time;
    private String status;
    private OperationDtoOutput operation;
    private UserDtoOutput user;

}
