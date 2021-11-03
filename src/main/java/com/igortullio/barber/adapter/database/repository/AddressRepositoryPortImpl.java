package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.AddressEntity;
import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.core.domain.Address;
import com.igortullio.barber.core.domain.City;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.AddressInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.AddressNotFoundException;
import com.igortullio.barber.core.exception.not_found.CityNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class AddressRepositoryPortImpl implements RepositoryPort<Address> {

    private final AddressJpaRepository addressJpaRepository;
    private final CityRepositoryPortImpl cityRepositoryPort;
    private final ModelMapper modelMapper;

    public AddressRepositoryPortImpl(AddressJpaRepository addressJpaRepository,
                                     CityRepositoryPortImpl cityRepositoryPort,
                                     ModelMapper modelMapper) {
        this.addressJpaRepository = addressJpaRepository;
        this.cityRepositoryPort = cityRepositoryPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public Address find(Long id) {
        AddressEntity addressEntity = addressJpaRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        return modelMapper.map(addressEntity, Address.class);
    }

    @Override
    public Address save(Address address) {
        try {
            AddressEntity addressEntity = modelMapper.map(address, AddressEntity.class);

            City city = cityRepositoryPort.find(address.getCity().getId());
            addressEntity.setCity(modelMapper.map(city, CityEntity.class));

            addressEntity = addressJpaRepository.save(addressEntity);

            return modelMapper.map(addressEntity, Address.class);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public Address update(Long id, Address address) {
        try {
            Address addressInDB = find(id);
            addressInDB.setZipCode(address.getZipCode());
            addressInDB.setPlace(address.getPlace());
            addressInDB.setNumber(address.getNumber());
            addressInDB.setDistrict(address.getDistrict());
            addressInDB.setComplement(address.getComplement());
            addressInDB.setLatitude(address.getLatitude());
            addressInDB.setLongitude(address.getLongitude());

            City city = cityRepositoryPort.find(address.getCity().getId());
            addressInDB.setCity(city);

            AddressEntity addressEntity = modelMapper.map(addressInDB, AddressEntity.class);
            addressEntity = addressJpaRepository.save(addressEntity);

            return modelMapper.map(addressEntity, Address.class);
        } catch (CityNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            addressJpaRepository.deleteById(id);
            addressJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new AddressNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new AddressInUseException(id);
        }
    }

}
