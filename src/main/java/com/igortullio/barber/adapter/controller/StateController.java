package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.StateDtoInput;
import com.igortullio.barber.adapter.dto.output.StateDtoOutput;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.domain.State;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.StateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController extends AbstractController<StateDtoInput, StateDtoOutput> {

    private final StateService stateService;

    @Autowired
    public StateController(ModelMapper modelMapper, StateService stateService) {
        super(modelMapper);
        this.stateService = stateService;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StateDtoOutput> getAll(Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        PageBarber<State> statePageBarber = stateService.findAll(null, pageableBarber);
        List<StateDtoOutput> stateDtoOutputs = statePageBarber.getList()
                .stream()
                .map(state -> modelMapper.map(state, StateDtoOutput.class))
                .toList();

        return new PageImpl<>(stateDtoOutputs, pageable, statePageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public StateDtoOutput get(Long id) {
        State state = stateService.find(id);
        return modelMapper.map(state, StateDtoOutput.class);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public StateDtoOutput post(StateDtoInput stateDto) {
        State state = modelMapper.map(stateDto, State.class);
        return modelMapper.map(stateService.save(state), StateDtoOutput.class);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public StateDtoOutput put(Long id, StateDtoInput stateDto) {
        State state = modelMapper.map(stateDto, State.class);
        return modelMapper.map(stateService.update(id, state), StateDtoOutput.class);
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public void delete(Long id) {
        stateService.delete(id);
    }

}
