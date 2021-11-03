package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.StateDtoInput;
import com.igortullio.barber.adapter.dto.output.StateDtoOutput;
import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.service.StateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/states")
public class StateController extends AbstractController<StateDtoInput, StateDtoOutput> {

    private final StateService stateService;

    @Autowired
    public StateController(ModelMapper modelMapper, StateService stateService) {
        super(modelMapper);
        this.stateService = stateService;
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public StateDtoOutput get(@PathVariable Long id) {
        State state = stateService.find(id);
        return modelMapper.map(state, StateDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public StateDtoOutput post(@RequestBody @Valid StateDtoInput stateDto) {
        State state = modelMapper.map(stateDto, State.class);
        return modelMapper.map(stateService.save(state), StateDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public StateDtoOutput put(@PathVariable Long id, @RequestBody @Valid StateDtoInput stateDto) {
        State state = modelMapper.map(stateDto, State.class);
        return modelMapper.map(stateService.update(id, state), StateDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public void delete(@PathVariable Long id) {
        stateService.delete(id);
    }

}
