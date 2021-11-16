package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.controller.param.ScheduleParam;
import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.ScheduleDtoInput;
import com.igortullio.barber.adapter.dto.output.ScheduleDtoOutput;
import com.igortullio.barber.adapter.dto.output.ScheduleFindAllDtoOutput;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController extends AbstractController<ScheduleDtoInput, ScheduleDtoOutput> {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ModelMapper modelMapper, ScheduleService scheduleService) {
        super(modelMapper);
        this.scheduleService = scheduleService;
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleFindAllDtoOutput> getAll(ScheduleParam scheduleParam,
                                                 Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<ScheduleEntity> spec = SpecificationTemplate.scheduleParam(scheduleParam);
        PageBarber<Schedule> schedulePageBarber = scheduleService.findAll(spec, pageableBarber);

        List<ScheduleFindAllDtoOutput> scheduleDtoOutputs = schedulePageBarber.getList()
                .stream()
                .map(operation -> modelMapper.map(operation, ScheduleFindAllDtoOutput.class))
                .toList();

        return new PageImpl<>(scheduleDtoOutputs, pageable, schedulePageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public ScheduleDtoOutput get(Long id) {
        Schedule schedule = scheduleService.find(id);
        return modelMapper.map(schedule, ScheduleDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public ScheduleDtoOutput post(ScheduleDtoInput scheduleDto) {
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        return modelMapper.map(scheduleService.save(schedule), ScheduleDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public ScheduleDtoOutput put(Long id, ScheduleDtoInput scheduleDto) {
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        return modelMapper.map(scheduleService.update(id, schedule), ScheduleDtoOutput.class);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @Override
    public void delete(Long id) {
        scheduleService.delete(id);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @PutMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        scheduleService.confirm(id);
    }

    @RolesAllowed({ PermissionGroupEntity.ADMIN, PermissionGroupEntity.USER })
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        scheduleService.cancel(id);
    }

}
