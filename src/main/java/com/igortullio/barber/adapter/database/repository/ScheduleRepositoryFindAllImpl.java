package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.adapter.mapper.CycleAvoidingMappingContext;
import com.igortullio.barber.adapter.mapper.ScheduleMapper;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRepositoryFindAllImpl implements RepositoryFindAllPort<Schedule> {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleRepositoryFindAllImpl(ScheduleJpaRepository scheduleJpaRepository,
                                         ScheduleMapper scheduleMapper) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public PageBarber<Schedule> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<ScheduleEntity> spec = (Specification<ScheduleEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<Schedule> schedulePage = scheduleJpaRepository.findAll(spec, pageRequest)
                .map(scheduleEntity -> scheduleMapper.scheduleEntityToSchedule(scheduleEntity, new CycleAvoidingMappingContext()));

        return new PageBarber<>(schedulePage.getContent(), PageablePortMapper.of(schedulePage));
    }

}
