package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.dto.input.ScheduleDtoInput;
import com.igortullio.barber.adapter.dto.output.ScheduleDtoOutput;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/schedules")
public class ScheduleController extends AbstractController<ScheduleDtoInput, ScheduleDtoOutput> {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ModelMapper modelMapper, ScheduleService scheduleService) {
        super(modelMapper);
        this.scheduleService = scheduleService;
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public ScheduleDtoOutput get(@PathVariable Long id) {
        Schedule schedule = scheduleService.find(id);
        return modelMapper.map(schedule, ScheduleDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public ScheduleDtoOutput post(@RequestBody @Valid ScheduleDtoInput scheduleDto) {
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        return modelMapper.map(scheduleService.save(schedule), ScheduleDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public ScheduleDtoOutput put(@PathVariable Long id, @RequestBody @Valid ScheduleDtoInput scheduleDto) {
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        return modelMapper.map(scheduleService.update(id, schedule), ScheduleDtoOutput.class);
    }

    @RolesAllowed(PermissionGroupEntity.USER)
    @Override
    public void delete(@PathVariable Long id) {
        scheduleService.delete(id);
    }

}
