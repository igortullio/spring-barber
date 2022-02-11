package com.igortullio.barber.adapter.database.repository;

import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.mapper.PermissionGroupMapper;
import com.igortullio.barber.adapter.mapper.UserMapper;
import com.igortullio.barber.core.domain.User;
import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.in_use.UserInUseException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import com.igortullio.barber.core.exception.not_found.UserNotFoundException;
import com.igortullio.barber.core.port.RepositoryPort;
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
    private final UserMapper userMapper;
    private final PermissionGroupMapper permissionGroupMapper;

    public UserRepositoryPortImpl(UserJpaRepository userJpaRepository,
                                  PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                  PasswordEncoder passwordEncoder,
                                  UserMapper userMapper,
                                  PermissionGroupMapper permissionGroupMapper) {
        this.userJpaRepository = userJpaRepository;
        this.permissionGroupRepositoryPort = permissionGroupRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.permissionGroupMapper = permissionGroupMapper;
    }

    @Override
    public User find(Long id) {
        UserEntity userEntity = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.userEntityToUser(userEntity);
    }

    @Override
    public User save(User user) {
        try {
            UserEntity userEntity = userMapper.userToUserEntity(user);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

            Set<PermissionGroupEntity> permissionGroupSet = userEntity.getPermissionGroupSet()
                    .stream()
                    .map(permissionGroupEntity -> permissionGroupRepositoryPort.find(permissionGroupEntity.getId()))
                    .map(permissionGroupMapper::permissionGroupToPermissionGroupEntity)
                    .collect(Collectors.toSet());
            userEntity.setPermissionGroupSet(permissionGroupSet);

            userEntity = userJpaRepository.save(userEntity);
            return userMapper.userEntityToUser(userEntity);
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

        UserEntity userEntity = userMapper.userToUserEntity(userInDB);
        userEntity = userJpaRepository.save(userEntity);

        return userMapper.userEntityToUser(userEntity);
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
