package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.port.RepositoryPort;

public class OperationService implements InterfaceService<Operation> {

    private final RepositoryPort<Operation> operationRepository;

    public OperationService(RepositoryPort<Operation> operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation find(Long id) {
        return operationRepository.find(id);
    }

    @Override
    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public Operation update(Long id, Operation operation) {
        return operationRepository.update(id, operation);
    }

    @Override
    public void delete(Long id) {
        operationRepository.delete(id);
    }

}
