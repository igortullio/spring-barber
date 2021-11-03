package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public abstract class AbstractDtoOutput {

    private Long id;
    private OffsetDateTime dateCreate;
    private OffsetDateTime dateUpdate;

}
