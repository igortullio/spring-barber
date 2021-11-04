package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.OperationDtoInput;
import com.igortullio.barber.adapter.dto.output.OperationDtoOutput;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.service.OperationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

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
