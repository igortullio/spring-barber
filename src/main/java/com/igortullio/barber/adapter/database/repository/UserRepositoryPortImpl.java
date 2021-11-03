package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.UserInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.UserNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserRepositoryPortImpl implements RepositoryPort<User> {

    private final UserJpaRepository userJpaRepository;
    private final PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserRepositoryPortImpl(UserJpaRepository userJpaRepository,
                                  PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                  PasswordEncoder passwordEncoder,
                                  ModelMapper modelMapper) {
        this.userJpaRepository = userJpaRepository;
        this.permissionGroupRepositoryPort = permissionGroupRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User find(Long id) {
        UserEntity userEntity = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public User save(User user) {
        try {
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

            Set<PermissionGroupEntity> permissionGroupSet = userEntity.getPermissionGroupSet()
                    .stream()
                    .map(permissionGroupEntity -> permissionGroupRepositoryPort.find(permissionGroupEntity.getId()))
                    .map(permissionGroup -> modelMapper.map(permissionGroup, PermissionGroupEntity.class))
                    .collect(Collectors.toSet());
            userEntity.setPermissionGroupSet(permissionGroupSet);

            userEntity = userJpaRepository.save(userEntity);
            return modelMapper.map(userEntity, User.class);
        } catch (AbstractNotFoundException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

    @Override
    public User update(Long id, User user) {
        User userInDB = find(id);
        userInDB.setName(user.getName());
        userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userInDB.setEmail(user.getEmail());
        userInDB.setPhone(user.getPhone());

        UserEntity stateEntity = modelMapper.map(userInDB, UserEntity.class);
        stateEntity = userJpaRepository.save(stateEntity);

        return modelMapper.map(stateEntity, User.class);
    }

    @Override
    public void delete(Long id) {
        try {
            userJpaRepository.deleteById(id);
            userJpaRepository.flush();
        } catch (EmptyResultDataAccessException exception) {
            throw new UserNotFoundException(id);
        } catch (DataIntegrityViolationException exception) {
            throw new UserInUseException(id);
        }
    }

}
