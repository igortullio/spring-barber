package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import com.igortullio.barber.core.port.RepositoryPort;
import com.igortullio.barber.core.port.RepositorySchedulePort;

import java.time.LocalDateTime;
import java.time.OffsetTime;

public class ScheduleService implements InterfaceService<Schedule>, InterfaceFindAllService<Schedule> {

    private final RepositoryPort<Schedule> scheduleRepository;
    private final RepositorySchedulePort repositorySchedulePort;
    private final RepositoryFindAllPort<Schedule> scheduleRepositoryFindAll;
    private final RepositoryPort<Operation> operationRepository;

    public ScheduleService(RepositoryPort<Schedule> scheduleRepository,
                           RepositorySchedulePort repositorySchedulePort,
                           RepositoryFindAllPort<Schedule> scheduleRepositoryFindAll,
                           RepositoryPort<Operation> operationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.repositorySchedulePort = repositorySchedulePort;
        this.scheduleRepositoryFindAll = scheduleRepositoryFindAll;
        this.operationRepository = operationRepository;
    }

    @Override
    public PageBarber<Schedule> findAll(Object o, PageableBarber pageableBarber) {
        return scheduleRepositoryFindAll.findAll(o, pageableBarber);
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

    public void confirm(Long id) {
        repositorySchedulePort.confirm(id);
    }

    public void cancel(Long id) {
        repositorySchedulePort.cancel(id);
    }

    private void verify(Schedule schedule) {
        Operation operation = operationRepository.find(schedule.getOperation().getId());

        verifyIfDateTimeNotPassed(schedule);
        verifyDayIsCorrect(schedule, operation);
        verifyFormatTime(schedule);
        verifyIfInBounds(schedule, operation);
        verifyIfTimeAlreadyExists(schedule, operation);
    }

    private void verifyIfDateTimeNotPassed(Schedule schedule) {
        if (!LocalDateTime.now().isBefore(schedule.getDateTime().toLocalDateTime())) {
            throw new BarberException("DateTime (" + schedule.getDateTime() + ") has passed");
        }
    }

    private void verifyDayIsCorrect(Schedule schedule, Operation operation) {
        if (!schedule.getDateTime().getDayOfWeek().equals(operation.getDay())) {
            throw new BarberException("Day (" + schedule.getDateTime().getDayOfWeek() + ") not match of operation day (" + operation.getDay() + ")");
        }
    }

    private void verifyFormatTime(Schedule schedule) {
        int minute = schedule.getDateTime().getMinute();
        if (minute != 0 && minute != 30) {
            throw new BarberException("Time (" + schedule.getDateTime().getHour() + ":" + minute + ") in wrong format");
        }
    }

    private void verifyIfInBounds(Schedule schedule, Operation operation) {
        OffsetTime scheduleTime = schedule.getDateTime().toOffsetTime();

        OffsetTime openTime = operation.getOpenTime();
        OffsetTime closeTime = operation.getCloseTime();

        boolean isInBetween = false;
        if (closeTime.isAfter(openTime)) {
            if (openTime.isBefore(scheduleTime) && closeTime.isAfter(scheduleTime)) {
                isInBetween = true;
            }
        } else if (scheduleTime.isAfter(openTime) || scheduleTime.isBefore(closeTime)) {
            isInBetween = true;
        }

        if (!isInBetween) {
            throw new BarberException("Time (" + scheduleTime + ") out of bounds (" + openTime + "-" + closeTime + ")");
        }
    }

    private void verifyIfTimeAlreadyExists(Schedule schedule, Operation operation) {
        if (operation.getScheduleSet().stream().anyMatch(s -> s.getDateTime().equals(schedule.getDateTime()))) {
            throw new BarberException("Time (" + schedule.getDateTime() + ") already exists");
        }
    }

}
