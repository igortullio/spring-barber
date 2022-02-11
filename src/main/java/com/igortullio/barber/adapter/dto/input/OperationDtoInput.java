package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.OffsetTime;

@Getter
@Setter
public class OperationDtoInput extends AbstractDtoInput {

    @NotNull
    private DayOfWeek day;

    @NotNull
    private OffsetTime openTime;

    @NotNull
    private OffsetTime closeTime;

    @Valid
    @NotNull
    private BarbershopOperationDtoInput barbershop;

    @Getter
    @Setter
    public static class BarbershopOperationDtoInput extends AbstractDtoInput {

    }

}
