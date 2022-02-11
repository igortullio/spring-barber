package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.StateEntity;
import com.igortullio.barber.adapter.dto.input.CityDtoInput;
import com.igortullio.barber.adapter.dto.input.StateDtoInput;
import com.igortullio.barber.adapter.dto.output.StateDtoOutput;
import com.igortullio.barber.core.domain.State;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {

    StateDtoOutput stateToStateDtoOutput(State state);

    State stateDtoInputToState(StateDtoInput stateDtoInput);

    State stateDtoCityInputToState(CityDtoInput.StateDtoCityInput stateDtoCityInput);

    StateEntity stateToStateEntity(State state);

    State stateEntityToState(StateEntity stateEntity);

}
