package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.dto.input.AddressDtoInput;
import com.igortullio.barber.adapter.dto.input.CityDtoInput;
import com.igortullio.barber.adapter.dto.output.CityDtoOutput;
import com.igortullio.barber.core.domain.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { StateMapper.class })
public interface CityMapper {

    CityDtoOutput cityToCityDtoOutput(City city);

    City cityDtoInputToCity(CityDtoInput cityDtoInput);

    City cityAddressDtoInputToCity(AddressDtoInput.CityAddressDtoInput cityAddressDtoInput);

    CityEntity cityToCityEntity(City city);

    City cityEntityToCity(CityEntity cityEntity);

}
