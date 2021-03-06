package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.OperationDtoInput;
import com.igortullio.barber.adapter.dto.output.OperationDtoOutput;
import com.igortullio.barber.adapter.mapper.CycleAvoidingMappingContext;
import com.igortullio.barber.adapter.mapper.OperationMapper;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.OperationService;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController implements InterfaceController<OperationDtoInput, OperationDtoOutput> {

    private final ZoneOffset jvmTzOffset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();

    private final OperationService operationService;
    private final OperationMapper operationMapper;

    @Autowired
    public OperationController(OperationService operationService, OperationMapper operationMapper) {
        this.operationService = operationService;
        this.operationMapper = operationMapper;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OperationDtoOutput> getAll(@RequestParam Long barbershopId,
                                           Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<OperationEntity> spec = SpecificationTemplate.operationBarbershopId(barbershopId);
        PageBarber<Operation> operationPageBarber = operationService.findAll(spec, pageableBarber);

        List<OperationDtoOutput> operationDtoOutputs = operationPageBarber.getList()
                .stream()
                .map(operation -> operationMapper.operationToOperationDtoOutput(operation, new CycleAvoidingMappingContext()))
                .toList();

        return new PageImpl<>(operationDtoOutputs, pageable, operationPageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public OperationDtoOutput get(Long id) {
        Operation operation = operationService.find(id);
        return operationMapper.operationToOperationDtoOutput(operation, new CycleAvoidingMappingContext());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public OperationDtoOutput post(OperationDtoInput operationDto) {
        operationDto.setOpenTime(operationDto.getOpenTime().withOffsetSameInstant(jvmTzOffset));
        operationDto.setCloseTime(operationDto.getCloseTime().withOffsetSameInstant(jvmTzOffset));

        Operation operation = operationMapper.operationDtoInputToOperation(operationDto);
        return operationMapper.operationToOperationDtoOutput(operationService.save(operation), new CycleAvoidingMappingContext());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public OperationDtoOutput put(Long id, OperationDtoInput operationDto) {
        operationDto.setOpenTime(operationDto.getOpenTime().withOffsetSameInstant(jvmTzOffset));
        operationDto.setCloseTime(operationDto.getCloseTime().withOffsetSameInstant(jvmTzOffset));

        Operation operation = operationMapper.operationDtoInputToOperation(operationDto);
        return operationMapper.operationToOperationDtoOutput(operationService.update(id, operation), new CycleAvoidingMappingContext());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER })
    @Override
    public void delete(Long id) {
        operationService.delete(id);
    }

}
