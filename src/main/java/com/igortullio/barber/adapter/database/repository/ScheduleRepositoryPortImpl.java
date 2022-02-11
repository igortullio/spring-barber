package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.mapper.CycleAvoidingMappingContext;
import com.igortullio.barber.adapter.mapper.OperationMapper;
import com.igortullio.barber.adapter.mapper.ScheduleMapper;
import com.igortullio.barber.adapter.mapper.UserMapper;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRepositoryPortImpl implements RepositoryPort<Schedule>, RepositorySchedulePort {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final UserRepositoryPortImpl userRepositoryPort;
    private final OperationRepositoryPortImpl operationRepositoryPort;
    private final ScheduleMapper scheduleMapper;
    private final UserMapper userMapper;
    private final OperationMapper operationMapper;

    public ScheduleRepositoryPortImpl(ScheduleJpaRepository scheduleJpaRepository,
                                      UserRepositoryPortImpl userRepositoryPort,
                                      OperationRepositoryPortImpl operationRepositoryPort,
                                      ScheduleMapper scheduleMapper,
                                      UserMapper userMapper,
                                      OperationMapper operationMapper) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.userRepositoryPort = userRepositoryPort;
        this.operationRepositoryPort = operationRepositoryPort;
        this.scheduleMapper = scheduleMapper;
        this.userMapper = userMapper;
        this.operationMapper = operationMapper;
    }

    @Override
    public Schedule find(Long id) {
        ScheduleEntity scheduleEntity = scheduleJpaRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        return scheduleMapper.scheduleEntityToSchedule(scheduleEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Schedule save(Schedule schedule) {
        try {
            ScheduleEntity scheduleEntity = scheduleMapper.scheduleToScheduleEntity(schedule, new CycleAvoidingMappingContext());

            UserEntity userLogged = SecurityUtil.getUserLogged();
            User user = userRepositoryPort.find(userLogged.getId());
            scheduleEntity.setUser(userMapper.userToUserEntity(user));

            Operation operation = operationRepositoryPort.find(scheduleEntity.getOperation().getId());
            scheduleEntity.setOperation(operationMapper.operationToOperationEntity(operation, new CycleAvoidingMappingContext()));

            scheduleEntity.setStatus(ScheduleStatus.CREATED);
            scheduleEntity = scheduleJpaRepository.save(scheduleEntity);
            return scheduleMapper.scheduleEntityToSchedule(scheduleEntity, new CycleAvoidingMappingContext());
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

            ScheduleEntity scheduleEntity = scheduleMapper.scheduleToScheduleEntity(scheduleInDB, new CycleAvoidingMappingContext());
            scheduleEntity = scheduleJpaRepository.save(scheduleEntity);

            return scheduleMapper.scheduleEntityToSchedule(scheduleEntity, new CycleAvoidingMappingContext());
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

        ScheduleEntity scheduleEntity = scheduleMapper.scheduleToScheduleEntity(scheduleInDB, new CycleAvoidingMappingContext());
        scheduleJpaRepository.save(scheduleEntity);
    }

    @Override
    public void cancel(Long id) {
        Schedule scheduleInDB = find(id);
        scheduleInDB.setStatus(ScheduleStatus.CANCELED);

        verifyUserScheduleAndBarbershopOwner(scheduleInDB);

        ScheduleEntity scheduleEntity = scheduleMapper.scheduleToScheduleEntity(scheduleInDB, new CycleAvoidingMappingContext());
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
