package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.mapper.BarbershopMapper;
import com.igortullio.barber.adapter.mapper.CycleAvoidingMappingContext;
import com.igortullio.barber.adapter.mapper.OperationMapper;
import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.OperationInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.BarbershopNotFoundException;
import com.igortullio.barber.core.exception.not_found.OperationNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Objects;

@Component
public class OperationRepositoryPortImpl implements RepositoryPort<Operation> {

    private final OperationJpaRepository operationJpaRepository;
    private final BarbershopRepositoryPortImpl barbershopRepositoryPort;
    private final OperationMapper operationMapper;
    private final BarbershopMapper barbershopMapper;

    public OperationRepositoryPortImpl(OperationJpaRepository operationJpaRepository,
                                       BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                       OperationMapper operationMapper,
                                       BarbershopMapper barbershopMapper) {
        this.operationJpaRepository = operationJpaRepository;
        this.barbershopRepositoryPort = barbershopRepositoryPort;
        this.operationMapper = operationMapper;
        this.barbershopMapper = barbershopMapper;
    }

    @Override
    public Operation find(Long id) {
        OperationEntity operationEntity = operationJpaRepository.findById(id)
                .orElseThrow(() -> new OperationNotFoundException(id));

        return operationMapper.operationEntityToOperation(operationEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Operation save(Operation operation) {
        try {
            OperationEntity operationEntity = operationMapper.operationToOperationEntity(operation, new CycleAvoidingMappingContext());

            Barbershop barbershop = barbershopRepositoryPort.find(operation.getBarbershop().getId());
            operationEntity.setBarbershop(barbershopMapper.barbershopToBarbershopEntity(barbershop));

            existsDayInBarbershopWithAnotherId(operation.getDay(), barbershop, null);

            operationEntity = operationJpaRepository.save(operationEntity);
            return operationMapper.operationEntityToOperation(operationEntity, new CycleAvoidingMappingContext());
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

            OperationEntity operationEntity = operationMapper.operationToOperationEntity(operationInDB, new CycleAvoidingMappingContext());
            operationEntity = operationJpaRepository.save(operationEntity);

            return operationMapper.operationEntityToOperation(operationEntity, new CycleAvoidingMappingContext());
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
        return operationMapper.operationEntityToOperation(operationEntity, new CycleAvoidingMappingContext());
    }

    private void existsDayInBarbershopWithAnotherId(DayOfWeek day, Barbershop barbershop, Long id) {
        try {
            BarbershopEntity barbershopEntity = barbershopMapper.barbershopToBarbershopEntity(barbershop);
            Operation operation = findByDayAndBarbershop(day, barbershopEntity);

            if (Objects.isNull(id) || !id.equals(operation.getId())) {
                throw new BarberException("Operation with day (" + operation.getDay() + ") already exists in the barbershop (" + operation.getBarbershop().getName() + ")");
            }
        } catch (OperationNotFoundException ignore) { /* Operation not exists */ }
    }

}
