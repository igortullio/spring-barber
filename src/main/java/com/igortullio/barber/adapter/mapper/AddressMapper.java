package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.AddressEntity;
import com.igortullio.barber.adapter.dto.input.AddressDtoInput;
import com.igortullio.barber.adapter.dto.input.BarbershopDtoInput;
import com.igortullio.barber.adapter.dto.output.AddressDtoOutput;
import com.igortullio.barber.adapter.dto.output.BarbershopFindAllDtoOutput;
import com.igortullio.barber.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { CityMapper.class })
public interface AddressMapper {

    AddressDtoOutput addressToAddressDtoOutput(Address address);

    BarbershopFindAllDtoOutput.AddressDtoBarbershopFindAllOutput addressToAddressDtoBarbershopFindAllOutput(Address address);

    Address addressDtoInputToAddress(AddressDtoInput addressDtoInput);

    Address addressBarbershopDtoInputToAddress(BarbershopDtoInput.AddressBarbershopDtoInput addressBarbershopDtoInput);

    Address addressEntityToAddress(AddressEntity addressEntity);

    AddressEntity addressToAddressEntity(Address address);

}
