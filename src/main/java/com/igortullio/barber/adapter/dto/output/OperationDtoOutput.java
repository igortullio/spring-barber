package com.igortullio.barber.adapter.dto.output;

import com.igortullio.barber.adapter.dto.input.AbstractDtoInput;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class OperationDtoOutput extends AbstractDtoOutput {

    private DayOfWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;
    private BarbershopOperationDtoOutput barbershop;

    @Getter
    @Setter
    private static class BarbershopOperationDtoOutput extends AbstractDtoInput {

        private String name;
        private Boolean active;

    }

}
