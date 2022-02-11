package com.igortullio.barber.adapter.config;

import com.igortullio.barber.adapter.database.repository.AddressJpaRepository;
import com.igortullio.barber.adapter.database.repository.AddressRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.BarbershopJpaRepository;
import com.igortullio.barber.adapter.database.repository.BarbershopRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.BarbershopRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.CityJpaRepository;
import com.igortullio.barber.adapter.database.repository.CityRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.CityRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.OperationJpaRepository;
import com.igortullio.barber.adapter.database.repository.OperationRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.OperationRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.PermissionGroupJpaRepository;
import com.igortullio.barber.adapter.database.repository.PermissionGroupRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.PermissionGroupRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.PermissionJpaRepository;
import com.igortullio.barber.adapter.database.repository.PermissionRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.PermissionRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.ScheduleJpaRepository;
import com.igortullio.barber.adapter.database.repository.ScheduleRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.ScheduleRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.StateJpaRepository;
import com.igortullio.barber.adapter.database.repository.StateRepositoryFindAllImpl;
import com.igortullio.barber.adapter.database.repository.StateRepositoryPortImpl;
import com.igortullio.barber.adapter.database.repository.UserJpaRepository;
import com.igortullio.barber.adapter.database.repository.UserRepositoryPortImpl;
import com.igortullio.barber.adapter.mapper.AddressMapper;
import com.igortullio.barber.adapter.mapper.BarbershopMapper;
import com.igortullio.barber.adapter.mapper.CityMapper;
import com.igortullio.barber.adapter.mapper.OperationMapper;
import com.igortullio.barber.adapter.mapper.PermissionGroupMapper;
import com.igortullio.barber.adapter.mapper.PermissionMapper;
import com.igortullio.barber.adapter.mapper.ScheduleMapper;
import com.igortullio.barber.adapter.mapper.StateMapper;
import com.igortullio.barber.adapter.mapper.UserMapper;
import com.igortullio.barber.core.service.AddressService;
import com.igortullio.barber.core.service.BarbershopService;
import com.igortullio.barber.core.service.CityService;
import com.igortullio.barber.core.service.OperationService;
import com.igortullio.barber.core.service.PermissionGroupService;
import com.igortullio.barber.core.service.PermissionService;
import com.igortullio.barber.core.service.ScheduleService;
import com.igortullio.barber.core.service.StateService;
import com.igortullio.barber.core.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public OperationService operationService(OperationJpaRepository operationJpaRepository,
                                             BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                             OperationMapper operationMapper,
                                             BarbershopMapper barbershopMapper) {
        return new OperationService(
                new OperationRepositoryPortImpl(operationJpaRepository, barbershopRepositoryPort, operationMapper, barbershopMapper),
                new OperationRepositoryFindAllImpl(operationJpaRepository, operationMapper)
        );
    }

    @Bean
    public PermissionService permissionService(PermissionJpaRepository permissionJpaRepository,
                                               PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                               PermissionMapper permissionMapper,
                                               PermissionGroupMapper permissionGroupMapper) {
        return new PermissionService(
                new PermissionRepositoryPortImpl(permissionJpaRepository, permissionGroupRepositoryPort, permissionMapper, permissionGroupMapper),
                new PermissionRepositoryFindAllImpl(permissionJpaRepository, permissionMapper)
        );
    }

    @Bean
    public PermissionGroupService permissionGroupService(PermissionGroupJpaRepository permissionGroupJpaRepository,
                                                         PermissionGroupMapper permissionGroupMapper) {
        return new PermissionGroupService(
                new PermissionGroupRepositoryPortImpl(permissionGroupJpaRepository, permissionGroupMapper),
                new PermissionGroupRepositoryFindAllImpl(permissionGroupJpaRepository, permissionGroupMapper)
        );
    }

    @Bean
    public ScheduleService scheduleService(ScheduleJpaRepository scheduleJpaRepository,
                                           UserRepositoryPortImpl userRepositoryPort,
                                           OperationRepositoryPortImpl operationRepositoryPort,
                                           OperationJpaRepository operationJpaRepository,
                                           BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                           ScheduleMapper scheduleMapper,
                                           UserMapper userMapper,
                                           OperationMapper operationMapper,
                                           BarbershopMapper barbershopMapper) {
        ScheduleRepositoryPortImpl scheduleRepository = new ScheduleRepositoryPortImpl(scheduleJpaRepository, userRepositoryPort, operationRepositoryPort, scheduleMapper, userMapper, operationMapper);

        return new ScheduleService(
                scheduleRepository,
                scheduleRepository,
                new ScheduleRepositoryFindAllImpl(scheduleJpaRepository, scheduleMapper),
                new OperationRepositoryPortImpl(operationJpaRepository, barbershopRepositoryPort, operationMapper, barbershopMapper)
        );
    }

    @Bean
    public UserService userService(UserJpaRepository userJpaRepository,
                                   PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                   PasswordEncoder passwordEncoder,
                                   UserMapper userMapper,
                                   PermissionGroupMapper permissionGroupMapper) {
        return new UserService(new UserRepositoryPortImpl(userJpaRepository, permissionGroupRepositoryPort, passwordEncoder, userMapper, permissionGroupMapper));
    }

    @Bean
    public AddressService addressService(AddressJpaRepository addressJpaRepository,
                                         CityRepositoryPortImpl cityRepositoryPort,
                                         AddressMapper addressMapper,
                                         CityMapper cityMapper) {
        return new AddressService(new AddressRepositoryPortImpl(addressJpaRepository, cityRepositoryPort, addressMapper, cityMapper));
    }

    @Bean
    public BarbershopService barbershopService(BarbershopJpaRepository barbershopJpaRepository,
                                               AddressRepositoryPortImpl addressRepositoryPort,
                                               UserRepositoryPortImpl userRepositoryPort,
                                               BarbershopMapper barbershopMapper,
                                               AddressMapper addressMapper,
                                               UserMapper userMapper) {
        return new BarbershopService(
                new BarbershopRepositoryPortImpl(barbershopJpaRepository, addressRepositoryPort, userRepositoryPort, barbershopMapper, addressMapper, userMapper),
                new BarbershopRepositoryFindAllImpl(barbershopJpaRepository, barbershopMapper)
        );
    }

    @Bean
    public CityService cityService(CityJpaRepository cityJpaRepository,
                                   StateRepositoryPortImpl stateRepositoryPort,
                                   CityMapper cityMapper,
                                   StateMapper stateMapper) {
        return new CityService(
                new CityRepositoryPortImpl(cityJpaRepository, stateRepositoryPort, cityMapper, stateMapper),
                new CityRepositoryFindAllImpl(cityJpaRepository, cityMapper)
        );
    }

    @Bean
    public StateService stateService(StateJpaRepository stateJpaRepository,
                                     StateMapper stateMapper) {
        return new StateService(
                new StateRepositoryPortImpl(stateJpaRepository, stateMapper),
                new StateRepositoryFindAllImpl(stateJpaRepository, stateMapper)
        );
    }

}
