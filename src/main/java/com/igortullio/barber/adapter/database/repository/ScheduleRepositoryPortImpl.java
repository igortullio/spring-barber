package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.util.SecurityUtil;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.domain.enumeration.ScheduleStatus;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.ScheduleInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.BarbershopNotFoundException;
import com.igortullio.barber.core.exception.not_found.ScheduleNotFoundException;
import com.igortullio.barber.core.exception.not_found.UserNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import com.igortullio.barber.core.port.RepositorySchedulePort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRepositoryPortImpl implements RepositoryPort<Schedule>, RepositorySchedulePort {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final UserRepositoryPortImpl userRepositoryPort;
    private final OperationRepositoryPortImpl operationRepositoryPort;
    private final ModelMapper modelMapper;

    public ScheduleRepositoryPortImpl(ScheduleJpaRepository scheduleJpaRepository,
                                      UserRepositoryPortImpl userRepositoryPort,
                                      OperationRepositoryPortImpl operationRepositoryPort,
                                      ModelMapper modelMapper) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.userRepositoryPort = userRepositoryPort;
        this.operationRepositoryPort = operationRepositoryPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public Schedule find(Long id) {
        ScheduleEntity scheduleEntity = scheduleJpaRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        return modelMapper.map(scheduleEntity, Schedule.class);
    }

    @Override
    public Schedule save(Schedule schedule) {
        try {
            ScheduleEntity scheduleEntity = modelMapper.map(schedule, ScheduleEntity.class);

            UserEntity userLogged = SecurityUtil.getUserLogged();
            User user = userRepositoryPort.find(userLogged.getId());
            scheduleEntity.setUser(modelMapper.map(user, UserEntity.class));

            Operation operation = operationRepositoryPort.find(scheduleEntity.getOperation().getId());
            scheduleEntity.setOperation(modelMapper.map(operation, OperationEntity.class));

            scheduleEntity.setStatus(ScheduleStatus.CREATED);
            scheduleEntity = scheduleJpaRepository.save(scheduleEntity);
            return modelMapper.map(scheduleEntity, Schedule.class);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public Schedule update(Long id, Schedule schedule) {
        try {
            Schedule scheduleInDB = find(id);
            scheduleInDB.setDateTime(schedule.getDateTime());

            UserEntity userLogged = SecurityUtil.getUserLogged();
            verifyIfUserLoggedIsScheduleUser(scheduleInDB, userLogged);

            User user = userRepositoryPort.find(userLogged.getId());
            scheduleInDB.setUser(user);

            Operation operation = operationRepositoryPort.find(schedule.getOperation().getId());
            scheduleInDB.setOperation(operation);

            ScheduleEntity scheduleEntity = modelMapper.map(scheduleInDB, ScheduleEntity.class);
            scheduleEntity = scheduleJpaRepository.save(scheduleEntity);

            return modelMapper.map(scheduleEntity, Schedule.class);
        } catch (UserNotFoundException | BarbershopNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            scheduleJpaRepository.deleteById(id);
            scheduleJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new ScheduleNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new ScheduleInUseException(id);
        }
    }

    @Override
    public void confirm(Long id) {
        Schedule scheduleInDB = find(id);
        scheduleInDB.setStatus(ScheduleStatus.CONFIRMED);

        verifyBarbershopOwner(scheduleInDB);

        ScheduleEntity scheduleEntity = modelMapper.map(scheduleInDB, ScheduleEntity.class);
        scheduleJpaRepository.save(scheduleEntity);
    }

    @Override
    public void cancel(Long id) {
        Schedule scheduleInDB = find(id);
        scheduleInDB.setStatus(ScheduleStatus.CANCELED);

        verifyUserScheduleAndBarbershopOwner(scheduleInDB);

        ScheduleEntity scheduleEntity = modelMapper.map(scheduleInDB, ScheduleEntity.class);
        scheduleJpaRepository.save(scheduleEntity);
    }

    private void verifyUserScheduleAndBarbershopOwner(Schedule schedule) {
        UserEntity userLogged = SecurityUtil.getUserLogged();

        if (!userLogged.getId().equals(schedule.getOperation().getBarbershop().getOwner().getId())
                && !userLogged.getId().equals(schedule.getUser().getId())) {
            throw new BarberException("Logged user is not barbershop owner or client");
        }
    }

    private void verifyBarbershopOwner(Schedule schedule) {
        UserEntity userLogged = SecurityUtil.getUserLogged();

        if (!userLogged.getId().equals(schedule.getOperation().getBarbershop().getOwner().getId())) {
            throw new BarberException("Barbershop owner is not logged user");
        }
    }

    private void verifyIfUserLoggedIsScheduleUser(Schedule schedule, UserEntity userLogged) {
        if (!schedule.getUser().getId().equals(userLogged.getId())) {
            throw new BarberException("Schedule owner is not user logged");
        }
    }

}
