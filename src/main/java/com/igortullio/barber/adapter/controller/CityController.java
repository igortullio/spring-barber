package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.CityDtoInput;
import com.igortullio.barber.adapter.dto.output.CityDtoOutput;
import com.igortullio.barber.adapter.mapper.CityMapper;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController implements InterfaceController<CityDtoInput, CityDtoOutput> {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @Autowired
    protected CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CityDtoOutput> getAll(@RequestParam Long stateId,
                                      Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<CityEntity> spec = SpecificationTemplate.cityStateId(stateId);
        PageBarber<City> cityPageBarber = cityService.findAll(spec, pageableBarber);

        List<CityDtoOutput> cityDtoOutputs = cityPageBarber.getList()
                .stream()
                .map(cityMapper::cityToCityDtoOutput)
                .toList();

        return new PageImpl<>(cityDtoOutputs, pageable, cityPageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public CityDtoOutput get(Long id) {
        City city = cityService.find(id);
        return cityMapper.cityToCityDtoOutput(city);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public CityDtoOutput post(CityDtoInput cityDto) {
        City city = cityMapper.cityDtoInputToCity(cityDto);
        return cityMapper.cityToCityDtoOutput(cityService.save(city));
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public CityDtoOutput put(Long id, CityDtoInput cityDto) {
        City city = cityMapper.cityDtoInputToCity(cityDto);
        return cityMapper.cityToCityDtoOutput(cityService.update(id, city));
    }

    @RolesAllowed(PermissionGroup.ADMIN)
    @Override
    public void delete(Long id) {
        cityService.delete(id);
    }

}
