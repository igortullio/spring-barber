package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.dto.input.AddressDtoInput;
import com.igortullio.barber.adapter.dto.output.AddressDtoOutput;
import com.igortullio.barber.adapter.mapper.AddressMapper;
import com.igortullio.barber.core.domain.Address;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/addresses")
public class AddressController implements InterfaceController<AddressDtoInput, AddressDtoOutput> {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @Autowired
    protected AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public AddressDtoOutput get(@PathVariable Long id) {
        Address address = addressService.find(id);
        return addressMapper.addressToAddressDtoOutput(address);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public AddressDtoOutput post(AddressDtoInput addressDto) {
        Address address = addressMapper.addressDtoInputToAddress(addressDto);
        return addressMapper.addressToAddressDtoOutput(addressService.save(address));
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public AddressDtoOutput put(@PathVariable Long id, AddressDtoInput addressDto) {
        Address address = addressMapper.addressDtoInputToAddress(addressDto);
        return addressMapper.addressToAddressDtoOutput(addressService.update(id, address));
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }

}
