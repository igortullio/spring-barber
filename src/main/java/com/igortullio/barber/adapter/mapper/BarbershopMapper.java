package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.dto.input.BarbershopDtoInput;
import com.igortullio.barber.adapter.dto.input.OperationDtoInput;
import com.igortullio.barber.adapter.dto.output.BarbershopDtoOutput;
import com.igortullio.barber.adapter.dto.output.BarbershopFindAllDtoOutput;
import com.igortullio.barber.adapter.dto.output.OperationDtoOutput;
import com.igortullio.barber.core.domain.Barbershop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, UserMapper.class })
public interface BarbershopMapper {

    BarbershopDtoOutput barbershopToBarbershopDtoOutput(Barbershop barbershop);

    BarbershopFindAllDtoOutput barbershopToBarbershopFindAllDtoOutput(Barbershop barbershop);

    OperationDtoOutput.BarbershopOperationDtoOutput barbershopToBarbershopOperationDtoOutput(Barbershop barbershop);

    Barbershop barbershopDtoInputToBarbershop(BarbershopDtoInput barbershopDtoInput);

    Barbershop barbershopOperationDtoInputToBarbershop(OperationDtoInput.BarbershopOperationDtoInput barbershopOperationDtoInput);

    Barbershop barbershopEntityToBarbershop(BarbershopEntity barbershopEntity);

    BarbershopEntity barbershopToBarbershopEntity(Barbershop barbershop);

}
