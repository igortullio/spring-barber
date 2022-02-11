package com.igortullio.barber.adapter.dto.output;

import com.igortullio.barber.adapter.dto.input.AbstractDtoInput;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.OffsetTime;

@Getter
@Setter
public class OperationDtoOutput extends AbstractDtoOutput {

    private DayOfWeek day;
    private OffsetTime openTime;
    private OffsetTime closeTime;
    private BarbershopOperationDtoOutput barbershop;

    @Getter
    @Setter
    public static class BarbershopOperationDtoOutput extends AbstractDtoInput {

        private String name;
        private Boolean active;

    }

}
