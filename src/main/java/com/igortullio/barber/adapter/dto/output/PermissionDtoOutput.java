package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDtoOutput extends AbstractDtoOutput {

    private String name;
    private String description;
    private PermissionGroupDtoOutput group;

}
