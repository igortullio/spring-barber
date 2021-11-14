package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.mapper.PageablePortMapper;
import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BarbershopRepositoryFindAllImpl implements RepositoryFindAllPort<Barbershop> {

    private final BarbershopJpaRepository barbershopJpaRepository;
    private final ModelMapper modelMapper;

    public BarbershopRepositoryFindAllImpl(BarbershopJpaRepository barbershopJpaRepository, ModelMapper modelMapper) {
        this.barbershopJpaRepository = barbershopJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageBarber<Barbershop> findAll(Object specification, PageableBarber pageableBarber) {
        Specification<BarbershopEntity> spec = (Specification<BarbershopEntity>) specification;

        PageRequest pageRequest = PageRequest.of(pageableBarber.getPage(), pageableBarber.getSize());

        Page<Barbershop> barbershopPage = barbershopJpaRepository.findAll(spec, pageRequest)
                .map(barbershopEntity -> modelMapper.map(barbershopEntity, Barbershop.class));

        return new PageBarber<>(barbershopPage.getContent(), PageablePortMapper.of(barbershopPage));
    }

}
