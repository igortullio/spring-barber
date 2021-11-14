package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.BarbershopDtoInput;
import com.igortullio.barber.adapter.dto.output.BarbershopDtoOutput;
import com.igortullio.barber.adapter.dto.output.BarbershopFindAllDtoOutput;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.BarbershopService;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/barbershops")
public class BarbershopController extends AbstractController<BarbershopDtoInput, BarbershopDtoOutput> {

    private final BarbershopService barbershopService;

    @Autowired
    protected BarbershopController(ModelMapper modelMapper, BarbershopService barbershopService) {
        super(modelMapper);
        this.barbershopService = barbershopService;
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BarbershopFindAllDtoOutput> getAll(@RequestParam Long cityId,
                                                   Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<BarbershopEntity> spec = SpecificationTemplate.barbershopCityId(cityId);
        PageBarber<Barbershop> barbershopPageBarber = barbershopService.findAll(spec, pageableBarber);

        List<BarbershopFindAllDtoOutput> barbershopDtoOutputs = barbershopPageBarber.getList()
                .stream()
                .map(barbershop -> modelMapper.map(barbershop, BarbershopFindAllDtoOutput.class))
                .toList();

        return new PageImpl<>(barbershopDtoOutputs, pageable, barbershopPageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public BarbershopDtoOutput get(Long id) {
        Barbershop barbershop = barbershopService.find(id);
        return modelMapper.map(barbershop, BarbershopDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public BarbershopDtoOutput post(BarbershopDtoInput barbershopDto) {
        Barbershop barbershop = modelMapper.map(barbershopDto, Barbershop.class);
        return modelMapper.map(barbershopService.save(barbershop), BarbershopDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public BarbershopDtoOutput put(Long id, BarbershopDtoInput barbershopDto) {
        Barbershop barbershop = modelMapper.map(barbershopDto, Barbershop.class);
        return modelMapper.map(barbershopService.update(id, barbershop), BarbershopDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public void delete(Long id) {
        barbershopService.delete(id);
    }

}
