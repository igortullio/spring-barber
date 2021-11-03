package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.dto.input.AbstractDtoInput;
import com.igortullio.barber.adapter.dto.output.AbstractDtoOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface InterfaceController<I extends AbstractDtoInput, O extends AbstractDtoOutput> {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    O get(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    O post(@RequestBody @Valid I type);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    O put(@PathVariable Long id, @RequestBody @Valid I type);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

}
