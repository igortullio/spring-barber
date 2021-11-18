package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.dto.input.AddressDtoInput;
import com.igortullio.barber.adapter.dto.output.AddressDtoOutput;
import com.igortullio.barber.core.domain.Address;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/addresses")
public class AddressController extends AbstractController<AddressDtoInput, AddressDtoOutput> {

    private final AddressService addressService;

    @Autowired
    protected AddressController(ModelMapper modelMapper, AddressService addressService) {
        super(modelMapper);
        this.addressService = addressService;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public AddressDtoOutput get(@PathVariable Long id) {
        Address address = addressService.find(id);
        return modelMapper.map(address, AddressDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public AddressDtoOutput post(AddressDtoInput addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);
        return modelMapper.map(addressService.save(address), AddressDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public AddressDtoOutput put(@PathVariable Long id, AddressDtoInput addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);
        return modelMapper.map(addressService.update(id, address), AddressDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }

}
