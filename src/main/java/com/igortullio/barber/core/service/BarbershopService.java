package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.port.RepositoryPort;

public class BarbershopService implements InterfaceService<Barbershop> {

    private final RepositoryPort<Barbershop> barbershopRepository;

    public BarbershopService(RepositoryPort<Barbershop> barbershopRepository) {
        this.barbershopRepository = barbershopRepository;
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
