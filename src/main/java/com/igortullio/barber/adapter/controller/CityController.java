package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.CityDtoInput;
import com.igortullio.barber.adapter.dto.output.CityDtoOutput;
import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.service.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/cities")
public class CityController extends AbstractController<CityDtoInput, CityDtoOutput> {

    private final CityService cityService;

    @Autowired
    protected CityController(ModelMapper modelMapper, CityService cityService) {
        super(modelMapper);
        this.cityService = cityService;
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public CityDtoOutput get(Long id) {
        City city = cityService.find(id);
        return modelMapper.map(city, CityDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public CityDtoOutput post(CityDtoInput cityDto) {
        City city = modelMapper.map(cityDto, City.class);
        return modelMapper.map(cityService.save(city), CityDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public CityDtoOutput put(Long id, CityDtoInput cityDto) {
        City city = modelMapper.map(cityDto, City.class);
        return modelMapper.map(cityService.update(id, city), CityDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.ADMIN)
    @Override
    public void delete(Long id) {
        cityService.delete(id);
    }

}
