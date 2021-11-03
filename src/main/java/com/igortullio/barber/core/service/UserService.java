package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.port.RepositoryPort;

public class UserService implements InterfaceService<User> {

    private final RepositoryPort<User> userRepository;

    public UserService(RepositoryPort<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User find(Long id) {
        return userRepository.find(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return userRepository.update(id, user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
