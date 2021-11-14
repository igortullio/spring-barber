package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRepositoryFindAllImpl implements RepositoryFindAllPort<Schedule> {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final ModelMapper modelMapper;

    public ScheduleRepositoryFindAllImpl(ScheduleJpaRepository scheduleJpaRepository, ModelMapper modelMapper) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageBarber<Schedule> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<ScheduleEntity> spec = (Specification<ScheduleEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<Schedule> schedulePage = scheduleJpaRepository.findAll(spec, pageRequest)
                .map(scheduleEntity -> modelMapper.map(scheduleEntity, Schedule.class));

        return new PageBarber<>(schedulePage.getContent(), PageablePortMapper.of(schedulePage));
    }

}
