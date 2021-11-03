package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.AddressEntity;
import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.core.domain.Address;
import com.igortullio.barber.core.domain.Barbershop;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.BarbershopInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.AddressNotFoundException;
import com.igortullio.barber.core.exception.not_found.BarbershopNotFoundException;
import com.igortullio.barber.core.exception.not_found.UserNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class BarbershopRepositoryPortImpl implements RepositoryPort<Barbershop> {

    private final BarbershopJpaRepository barbershopJpaRepository;
    private final AddressRepositoryPortImpl addressRepositoryPort;
    private final UserRepositoryPortImpl userRepositoryPort;
    private final ModelMapper modelMapper;

    public BarbershopRepositoryPortImpl(BarbershopJpaRepository barbershopJpaRepository,
                                        AddressRepositoryPortImpl addressJpaRepository,
                                        UserRepositoryPortImpl userJpaRepository,
                                        ModelMapper modelMapper) {
        this.barbershopJpaRepository = barbershopJpaRepository;
        this.addressRepositoryPort = addressJpaRepository;
        this.userRepositoryPort = userJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Barbershop find(Long id) {
        BarbershopEntity barbershopEntity = barbershopJpaRepository.findById(id)
                .orElseThrow(() -> new BarbershopNotFoundException(id));

        return modelMapper.map(barbershopEntity, Barbershop.class);
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        try {
            BarbershopEntity barbershopEntity = modelMapper.map(barbershop, BarbershopEntity.class);

            Address address = addressRepositoryPort.find(barbershopEntity.getAddress().getId());
            barbershopEntity.setAddress(modelMapper.map(address, AddressEntity.class));

            User owner = userRepositoryPort.find(barbershopEntity.getOwner().getId());
            barbershopEntity.setOwner(modelMapper.map(owner, UserEntity.class));

            barbershopEntity = barbershopJpaRepository.save(barbershopEntity);
            return modelMapper.map(barbershopEntity, Barbershop.class);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public Barbershop update(Long id, Barbershop barbershop) {
        try {
            Barbershop barbershopInDB = find(id);
            barbershopInDB.setName(barbershop.getName());
            barbershopInDB.setActive(barbershop.getActive());
            barbershopInDB.setOpen(barbershop.getOpen());

            Address address = addressRepositoryPort.find(barbershop.getAddress().getId());
            barbershopInDB.setAddress(address);

            User owner = userRepositoryPort.find(barbershop.getOwner().getId());
            barbershopInDB.setOwner(owner);

            BarbershopEntity barbershopEntity = modelMapper.map(barbershopInDB, BarbershopEntity.class);
            barbershopEntity = barbershopJpaRepository.save(barbershopEntity);

            return modelMapper.map(barbershopEntity, Barbershop.class);
        } catch (AddressNotFoundException | UserNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            barbershopJpaRepository.deleteById(id);
            barbershopJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new BarbershopNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new BarbershopInUseException(id);
        }
    }

}
