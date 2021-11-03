package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.database.entity.enumeration.ScheduleStatusEntity;
import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.domain.Schedule;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.ScheduleInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.BarbershopNotFoundException;
import com.igortullio.barber.core.exception.not_found.ScheduleNotFoundException;
import com.igortullio.barber.core.exception.not_found.UserNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRepositoryPortImpl implements RepositoryPort<Schedule> {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final UserRepositoryPortImpl userRepositoryPort;
    private final BarbershopRepositoryPortImpl barbershopRepositoryPort;
    private final ModelMapper modelMapper;

    public ScheduleRepositoryPortImpl(ScheduleJpaRepository scheduleJpaRepository,
                                      UserRepositoryPortImpl userRepositoryPort,
                                      BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                      ModelMapper modelMapper) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.userRepositoryPort = userRepositoryPort;
        this.barbershopRepositoryPort = barbershopRepositoryPort;
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

            User user = userRepositoryPort.find(scheduleEntity.getUser().getId());
            scheduleEntity.setUser(modelMapper.map(user, UserEntity.class));

            Barbershop barbershop = barbershopRepositoryPort.find(scheduleEntity.getBarbershop().getId());
            scheduleEntity.setBarbershop(modelMapper.map(barbershop, BarbershopEntity.class));

            scheduleEntity.setStatus(ScheduleStatusEntity.CREATED);
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
            scheduleInDB.setDate(schedule.getDate());

            User user = userRepositoryPort.find(schedule.getUser().getId());
            scheduleInDB.setUser(user);

            Barbershop barbershop = barbershopRepositoryPort.find(schedule.getBarbershop().getId());
            scheduleInDB.setBarbershop(barbershop);

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

}
