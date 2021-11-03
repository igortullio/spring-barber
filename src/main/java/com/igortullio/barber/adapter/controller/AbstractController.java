package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.dto.input.AbstractDtoInput;
import com.igortullio.barber.adapter.dto.output.AbstractDtoOutput;
import org.modelmapper.ModelMapper;

public abstract class AbstractController<I extends AbstractDtoInput, O extends AbstractDtoOutput>
        implements InterfaceController<I, O> {

    protected final ModelMapper modelMapper;

    protected AbstractController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
