package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.port.RepositoryPort;

public class ScheduleService implements InterfaceService<Schedule> {

    private final RepositoryPort<Schedule> scheduleRepository;

    public ScheduleService(RepositoryPort<Schedule> scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule find(Long id) {
        return scheduleRepository.find(id);
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Long id, Schedule schedule) {
        return scheduleRepository.update(id, schedule);
    }

    @Override
    public void delete(Long id) {
        scheduleRepository.delete(id);
    }

}
