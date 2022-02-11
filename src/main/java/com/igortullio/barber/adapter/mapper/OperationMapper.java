package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.dto.input.OperationDtoInput;
import com.igortullio.barber.adapter.dto.input.ScheduleDtoInput;
import com.igortullio.barber.adapter.dto.output.OperationDtoOutput;
import com.igortullio.barber.core.domain.Operation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                BarbershopMapper.class,
                PermissionMapper.class,
                PermissionGroupMapper.class,
                UserMapper.class
        }
)
public interface OperationMapper {

    OperationDtoOutput operationToOperationDtoOutput(Operation operation, @Context CycleAvoidingMappingContext context);

    Operation operationDtoInputToOperation(OperationDtoInput operationDtoInput);

    Operation operationScheduleDtoInputToOperation(ScheduleDtoInput.OperationScheduleDtoInput operationScheduleDtoInput);

    OperationEntity operationToOperationEntity(Operation operation, @Context CycleAvoidingMappingContext context);

    Operation operationEntityToOperation(OperationEntity operationEntity, @Context CycleAvoidingMappingContext context);

}
