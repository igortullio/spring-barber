package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

@Getter
@Setter
public class ScheduleFindAllDtoOutput extends AbstractDtoOutput {

    private OffsetDateTime dateTime;
    private String status;
    private DayOfWeek operationDay;
    private UserDtoOutput user;

}
