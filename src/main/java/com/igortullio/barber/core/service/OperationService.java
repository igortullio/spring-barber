package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import com.igortullio.barber.core.port.RepositoryPort;

public class OperationService implements InterfaceService<Operation>, InterfaceFindAllService<Operation> {

    private final RepositoryPort<Operation> operationRepository;
    private final RepositoryFindAllPort<Operation> operationRepositoryFindAll;

    public OperationService(RepositoryPort<Operation> operationRepository,
                            RepositoryFindAllPort<Operation> operationRepositoryFindAll) {
        this.operationRepository = operationRepository;
        this.operationRepositoryFindAll = operationRepositoryFindAll;
    }

    @Override
    public PageBarber<Operation> findAll(Object o, PageableBarber pageableBarber) {
        return operationRepositoryFindAll.findAll(o, pageableBarber);
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
