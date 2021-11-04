package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class OperationDtoInput extends AbstractDtoInput {

    @NotNull
    private DayOfWeek day;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    @Valid
    @NotNull
    private BarbershopOperationDtoInput barbershop;

    @Getter
    @Setter
    private static class BarbershopOperationDtoInput extends AbstractDtoInput {

    }

}
