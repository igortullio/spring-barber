package com.igortullio.barber.adapter.mapper;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.dto.input.ScheduleDtoInput;
import com.igortullio.barber.adapter.dto.output.ScheduleDtoOutput;
import com.igortullio.barber.adapter.dto.output.ScheduleFindAllDtoOutput;
import com.igortullio.barber.core.domain.Schedule;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = { OperationMapper.class, UserMapper.class }, config = AbstractMapper.class)
public interface ScheduleMapper {

    ScheduleDtoOutput scheduleToScheduleDtoOutput(Schedule schedule);

    ScheduleFindAllDtoOutput scheduleToScheduleFindAllDtoOutput(Schedule schedule);

    Schedule scheduleDtoInputToSchedule(ScheduleDtoInput scheduleDtoInput);

    ScheduleEntity scheduleToScheduleEntity(Schedule schedule, @Context CycleAvoidingMappingContext context);

    Set<ScheduleEntity> scheduleToScheduleEntity(Set<Schedule> schedule);

    Schedule scheduleEntityToSchedule(ScheduleEntity scheduleEntity, @Context CycleAvoidingMappingContext context);

    Set<Schedule> scheduleEntityToSchedule(Set<ScheduleEntity> scheduleEntity);

}
