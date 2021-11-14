package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.core.domain.Operation;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OperationRepositoryFindAllImpl implements RepositoryFindAllPort<Operation> {

    private final OperationJpaRepository operationJpaRepository;
    private final ModelMapper modelMapper;

    public OperationRepositoryFindAllImpl(OperationJpaRepository operationJpaRepository, ModelMapper modelMapper) {
        this.operationJpaRepository = operationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageBarber<Operation> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<OperationEntity> spec = (Specification<OperationEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<Operation> operationPage = operationJpaRepository.findAll(spec, pageRequest)
                .map(operationEntity -> modelMapper.map(operationEntity, Operation.class));

        return new PageBarber<>(operationPage.getContent(), PageablePortMapper.of(operationPage));
    }

}
