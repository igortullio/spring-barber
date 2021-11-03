package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Getter
@Setter
public class ScheduleDtoInput extends AbstractDtoInput {

    @NotNull
    private OffsetDateTime date;

    @Valid
    @NotNull
    private BarbershopScheduleDtoInput barbershop;

    @Valid
    @NotNull
    private UserScheduleDtoInput user;

    @Getter
    @Setter
    private static class BarbershopScheduleDtoInput extends AbstractDtoInput {

    }

    @Getter
    @Setter
    private static class UserScheduleDtoInput extends AbstractDtoInput {

    }

}
