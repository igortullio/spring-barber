package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.dto.output.AbstractDtoOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface InterfaceControllerGetAll<O extends AbstractDtoOutput> {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<O> getAll(Pageable pageable);

}
