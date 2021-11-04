package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.port.RepositoryPort;

public class ScheduleService implements InterfaceService<Schedule> {

    private final RepositoryPort<Schedule> scheduleRepository;
    private final RepositoryPort<Operation> operationRepository;

    public ScheduleService(RepositoryPort<Schedule> scheduleRepository,
                           RepositoryPort<Operation> operationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Schedule find(Long id) {
        return scheduleRepository.find(id);
    }

    @Override
    public Schedule save(Schedule schedule) {
        verify(schedule);
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Long id, Schedule schedule) {
        verify(schedule);
        return scheduleRepository.update(id, schedule);
    }

    @Override
    public void delete(Long id) {
        scheduleRepository.delete(id);
    }

    private void verify(Schedule schedule) {
        Operation operation = operationRepository.find(schedule.getOperation().getId());

        verifyFormatTime(schedule);
        verifyIfInBounds(schedule, operation);
        verifyIfTimeAlreadyExists(schedule, operation);
    }

    private void verifyFormatTime(Schedule schedule) {
        int minute = schedule.getTime().getMinute();
        if (minute != 0 && minute != 30) {
            throw new BarberException("Time (" + schedule.getTime() + ") in wrong format");
        }
    }

    private void verifyIfInBounds(Schedule schedule, Operation operation) {
        if (schedule.getTime().compareTo(operation.getOpenTime()) < 0
                || schedule.getTime().compareTo(operation.getCloseTime()) > 0) {
            throw new BarberException("Time (" + schedule.getTime() + ") out of bounds");
        }
    }

    private void verifyIfTimeAlreadyExists(Schedule schedule, Operation operation) {
        if (operation.getScheduleSet().stream().anyMatch(s -> s.getTime().equals(schedule.getTime()))) {
            throw new BarberException("Time (" + schedule.getTime() + ") already exists");
        }
    }

}
