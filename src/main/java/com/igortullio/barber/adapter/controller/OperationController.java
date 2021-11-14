package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.OperationDtoInput;
import com.igortullio.barber.adapter.dto.output.OperationDtoOutput;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.OperationService;
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
@RequestMapping("/operations")
public class OperationController extends AbstractController<OperationDtoInput, OperationDtoOutput> {

    private final OperationService operationService;

    @Autowired
    public OperationController(ModelMapper modelMapper, OperationService operationService) {
        super(modelMapper);
        this.operationService = operationService;
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OperationDtoOutput> getAll(@RequestParam Long barbershopId,
                                           Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<OperationEntity> spec = SpecificationTemplate.operationBarbershopId(barbershopId);
        PageBarber<Operation> operationPageBarber = operationService.findAll(spec, pageableBarber);

        List<OperationDtoOutput> operationDtoOutputs = operationPageBarber.getList()
                .stream()
                .map(operation -> modelMapper.map(operation, OperationDtoOutput.class))
                .toList();

        return new PageImpl<>(operationDtoOutputs, pageable, operationPageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public OperationDtoOutput get(Long id) {
        Operation operation = operationService.find(id);
        return modelMapper.map(operation, OperationDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public OperationDtoOutput post(OperationDtoInput operationDto) {
        Operation operation = modelMapper.map(operationDto, Operation.class);
        return modelMapper.map(operationService.save(operation), OperationDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public OperationDtoOutput put(Long id, OperationDtoInput operationDto) {
        Operation operation = modelMapper.map(operationDto, Operation.class);
        return modelMapper.map(operationService.update(id, operation), OperationDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public void delete(Long id) {
        operationService.delete(id);
    }

}
