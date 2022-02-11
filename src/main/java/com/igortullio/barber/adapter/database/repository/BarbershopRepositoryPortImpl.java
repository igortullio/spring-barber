package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.mapper.AddressMapper;
import com.igortullio.barber.adapter.mapper.BarbershopMapper;
import com.igortullio.barber.adapter.mapper.UserMapper;
import com.igortullio.barber.adapter.util.SecurityUtil;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class BarbershopRepositoryPortImpl implements RepositoryPort<Barbershop> {

    private final BarbershopJpaRepository barbershopJpaRepository;
    private final AddressRepositoryPortImpl addressRepositoryPort;
    private final UserRepositoryPortImpl userRepositoryPort;
    private final BarbershopMapper barbershopMapper;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    public BarbershopRepositoryPortImpl(BarbershopJpaRepository barbershopJpaRepository,
                                        AddressRepositoryPortImpl addressJpaRepository,
                                        UserRepositoryPortImpl userJpaRepository,
                                        BarbershopMapper barbershopMapper,
                                        AddressMapper addressMapper,
                                        UserMapper userMapper) {
        this.barbershopJpaRepository = barbershopJpaRepository;
        this.addressRepositoryPort = addressJpaRepository;
        this.userRepositoryPort = userJpaRepository;
        this.barbershopMapper = barbershopMapper;
        this.addressMapper = addressMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Barbershop find(Long id) {
        BarbershopEntity barbershopEntity = barbershopJpaRepository.findById(id)
                .orElseThrow(() -> new BarbershopNotFoundException(id));

        return barbershopMapper.barbershopEntityToBarbershop(barbershopEntity);
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        try {
            BarbershopEntity barbershopEntity = barbershopMapper.barbershopToBarbershopEntity(barbershop);

            Address address = addressRepositoryPort.find(barbershopEntity.getAddress().getId());
            barbershopEntity.setAddress(addressMapper.addressToAddressEntity(address));

            UserEntity userLogged = SecurityUtil.getUserLogged();
            User owner = userRepositoryPort.find(userLogged.getId());
            barbershopEntity.setOwner(userMapper.userToUserEntity(owner));

            barbershopEntity = barbershopJpaRepository.save(barbershopEntity);
            return barbershopMapper.barbershopEntityToBarbershop(barbershopEntity);
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

            Address address = addressRepositoryPort.find(barbershop.getAddress().getId());
            barbershopInDB.setAddress(address);

            UserEntity userLogged = SecurityUtil.getUserLogged();
            verifyIfUserLoggedIsOwner(barbershopInDB, userLogged);

            User owner = userRepositoryPort.find(userLogged.getId());
            barbershopInDB.setOwner(owner);

            BarbershopEntity barbershopEntity = barbershopMapper.barbershopToBarbershopEntity(barbershopInDB);
            barbershopEntity = barbershopJpaRepository.save(barbershopEntity);

            return barbershopMapper.barbershopEntityToBarbershop(barbershopEntity);
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

    private void verifyIfUserLoggedIsOwner(Barbershop barbershop, UserEntity userLogged) {
        if (!barbershop.getOwner().getId().equals(userLogged.getId())) {
            throw new BarberException("Barbershop owner is not user logged");
        }
    }

}
