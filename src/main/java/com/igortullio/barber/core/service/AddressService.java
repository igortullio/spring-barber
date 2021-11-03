package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.Address;
import com.igortullio.barber.core.port.RepositoryPort;

public class AddressService implements InterfaceService<Address> {

    private final RepositoryPort<Address> addressRepository;

    public AddressService(RepositoryPort<Address> addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address find(Long id) {
        return addressRepository.find(id);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Long id, Address address) {
        return addressRepository.update(id, address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.delete(id);
    }

}
