package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;
import com.igortullio.barber.core.port.RepositoryFindAllPort;
import com.igortullio.barber.core.port.RepositoryPort;

public class BarbershopService implements InterfaceService<Barbershop>, InterfaceFindAllService<Barbershop> {

    private final RepositoryPort<Barbershop> barbershopRepository;
    private final RepositoryFindAllPort<Barbershop> repositoryFindAll;

    public BarbershopService(RepositoryPort<Barbershop> barbershopRepository,
                             RepositoryFindAllPort<Barbershop> repositoryFindAll) {
        this.barbershopRepository = barbershopRepository;
        this.repositoryFindAll = repositoryFindAll;
    }

    @Override
    public PageBarber<Barbershop> findAll(Object o, PageableBarber pageableBarber) {
        return repositoryFindAll.findAll(o, pageableBarber);
    }

    @Override
    public Barbershop find(Long id) {
        return barbershopRepository.find(id);
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        return barbershopRepository.save(barbershop);
    }

    @Override
    public Barbershop update(Long id, Barbershop barbershop) {
        return barbershopRepository.update(id, barbershop);
    }

    @Override
    public void delete(Long id) {
        barbershopRepository.delete(id);
    }

}
