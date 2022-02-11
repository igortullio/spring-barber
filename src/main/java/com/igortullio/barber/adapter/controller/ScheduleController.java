package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.dto.input.ScheduleDtoInput;
import com.igortullio.barber.adapter.dto.output.ScheduleDtoOutput;
import com.igortullio.barber.adapter.dto.output.ScheduleFindAllDtoOutput;
import com.igortullio.barber.adapter.mapper.ScheduleMapper;
import com.igortullio.barber.adapter.specifications.SpecificationTemplate;
import com.igortullio.barber.core.domain.PermissionGroup;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.service.ScheduleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController implements InterfaceController<ScheduleDtoInput, ScheduleDtoOutput> {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleFindAllDtoOutput> getAll(@RequestParam(required = false) Long operationId,
                                                 Pageable pageable) {
        PageableBarber pageableBarber = PageablePortMapper.of(pageable);

        Specification<ScheduleEntity> spec = SpecificationTemplate.scheduleParam(operationId);
        PageBarber<Schedule> schedulePageBarber = scheduleService.findAll(spec, pageableBarber);

        List<ScheduleFindAllDtoOutput> scheduleDtoOutputs = schedulePageBarber.getList()
                .stream()
                .map(scheduleMapper::scheduleToScheduleFindAllDtoOutput)
                .toList();

        return new PageImpl<>(scheduleDtoOutputs, pageable, schedulePageBarber.getPageable().getTotalElements());
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @Override
    public ScheduleDtoOutput get(Long id) {
        Schedule schedule = scheduleService.find(id);
        return scheduleMapper.scheduleToScheduleDtoOutput(schedule);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.USER })
    @Override
    public ScheduleDtoOutput post(ScheduleDtoInput scheduleDto) {
        Schedule schedule = scheduleMapper.scheduleDtoInputToSchedule(scheduleDto);
        return scheduleMapper.scheduleToScheduleDtoOutput(scheduleService.save(schedule));
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.USER })
    @Override
    public ScheduleDtoOutput put(Long id, ScheduleDtoInput scheduleDto) {
        Schedule schedule = scheduleMapper.scheduleDtoInputToSchedule(scheduleDto);
        return scheduleMapper.scheduleToScheduleDtoOutput(scheduleService.update(id, schedule));
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.USER })
    @Override
    public void delete(Long id) {
        scheduleService.delete(id);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @PutMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        scheduleService.confirm(id);
    }

    @RolesAllowed({ PermissionGroup.ADMIN, PermissionGroup.BARBERSHOP_OWNER, PermissionGroup.USER })
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        scheduleService.cancel(id);
    }

}
