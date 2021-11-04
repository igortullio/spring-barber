package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.OperationInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.BarbershopNotFoundException;
import com.igortullio.barber.core.exception.not_found.OperationNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Objects;

@Component
public class OperationRepositoryPortImpl implements RepositoryPort<Operation> {

    private final OperationJpaRepository operationJpaRepository;
    private final BarbershopRepositoryPortImpl barbershopRepositoryPort;
    private final ModelMapper modelMapper;

    public OperationRepositoryPortImpl(OperationJpaRepository operationJpaRepository,
                                       BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                       ModelMapper modelMapper) {
        this.operationJpaRepository = operationJpaRepository;
        this.barbershopRepositoryPort = barbershopRepositoryPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public Operation find(Long id) {
        OperationEntity operationEntity = operationJpaRepository.findById(id)
                .orElseThrow(() -> new OperationNotFoundException(id));

        return modelMapper.map(operationEntity, Operation.class);
    }

    @Override
    public Operation save(Operation operation) {
        try {
            OperationEntity operationEntity = modelMapper.map(operation, OperationEntity.class);

            Barbershop barbershop = barbershopRepositoryPort.find(operation.getBarbershop().getId());
            operationEntity.setBarbershop(modelMapper.map(barbershop, BarbershopEntity.class));

            existsDayInBarbershopWithAnotherId(operation.getDay(), barbershop, null);

            operationEntity = operationJpaRepository.save(operationEntity);
            return modelMapper.map(operationEntity, Operation.class);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public Operation update(Long id, Operation operation) {
        try {
            Operation operationInDB = find(id);
            operationInDB.setDay(operation.getDay());
            operationInDB.setOpenTime(operation.getOpenTime());
            operationInDB.setCloseTime(operation.getCloseTime());

            Barbershop barbershop = barbershopRepositoryPort.find(operation.getBarbershop().getId());
            operationInDB.setBarbershop(barbershop);

            existsDayInBarbershopWithAnotherId(operationInDB.getDay(), barbershop, operationInDB.getId());

            OperationEntity operationEntity = modelMapper.map(operationInDB, OperationEntity.class);
            operationEntity = operationJpaRepository.save(operationEntity);

            return modelMapper.map(operationEntity, Operation.class);
        } catch (BarbershopNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            operationJpaRepository.deleteById(id);
            operationJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new OperationNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new OperationInUseException(id);
        }
    }

    protected Operation findByDayAndBarbershop(DayOfWeek day, BarbershopEntity barbershopEntity) {
        OperationEntity operationEntity = operationJpaRepository.findByDayAndBarbershop(day, barbershopEntity)
                .orElseThrow(() -> new OperationNotFoundException(day, barbershopEntity.getName()));
        return modelMapper.map(operationEntity, Operation.class);
    }

    private void existsDayInBarbershopWithAnotherId(DayOfWeek day, Barbershop barbershop, Long id) {
        try {
            BarbershopEntity barbershopEntity = modelMapper.map(barbershop, BarbershopEntity.class);
            Operation operation = findByDayAndBarbershop(day, barbershopEntity);

            if (Objects.isNull(id) || !id.equals(operation.getId())) {
                throw new BarberException("Operation with day (" + operation.getDay() + ") already exists in the barbershop (" + operation.getBarbershop().getName() + ")");
            }
        } catch (OperationNotFoundException ignore) { /* Operation not exists */ }
    }

}
