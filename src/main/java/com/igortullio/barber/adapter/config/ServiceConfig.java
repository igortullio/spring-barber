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
import com.igortullio.barber.core.service.AddressService;
import com.igortullio.barber.core.service.BarbershopService;
import com.igortullio.barber.core.service.CityService;
import com.igortullio.barber.core.service.OperationService;
import com.igortullio.barber.core.service.PermissionGroupService;
import com.igortullio.barber.core.service.PermissionService;
import com.igortullio.barber.core.service.ScheduleService;
import com.igortullio.barber.core.service.StateService;
import com.igortullio.barber.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public OperationService operationService(OperationJpaRepository operationJpaRepository,
                                             BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                             ModelMapper modelMapper) {
        return new OperationService(
                new OperationRepositoryPortImpl(operationJpaRepository, barbershopRepositoryPort, modelMapper),
                new OperationRepositoryFindAllImpl(operationJpaRepository, modelMapper)
        );
    }

    @Bean
    public PermissionService permissionService(PermissionJpaRepository permissionJpaRepository,
                                               PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                               ModelMapper modelMapper) {
        return new PermissionService(
                new PermissionRepositoryPortImpl(permissionJpaRepository, permissionGroupRepositoryPort, modelMapper),
                new PermissionRepositoryFindAllImpl(permissionJpaRepository, modelMapper)
        );
    }

    @Bean
    public PermissionGroupService permissionGroupService(PermissionGroupJpaRepository permissionGroupJpaRepository,
                                                         ModelMapper modelMapper) {
        return new PermissionGroupService(
                new PermissionGroupRepositoryPortImpl(permissionGroupJpaRepository, modelMapper),
                new PermissionGroupRepositoryFindAllImpl(permissionGroupJpaRepository, modelMapper)
        );
    }

    @Bean
    public ScheduleService scheduleService(ScheduleJpaRepository scheduleJpaRepository,
                                           UserRepositoryPortImpl userRepositoryPort,
                                           OperationRepositoryPortImpl operationRepositoryPort,
                                           OperationJpaRepository operationJpaRepository,
                                           BarbershopRepositoryPortImpl barbershopRepositoryPort,
                                           ModelMapper modelMapper) {
        ScheduleRepositoryPortImpl scheduleRepository = new ScheduleRepositoryPortImpl(scheduleJpaRepository, userRepositoryPort, operationRepositoryPort, modelMapper);

        return new ScheduleService(
                scheduleRepository,
                scheduleRepository,
                new ScheduleRepositoryFindAllImpl(scheduleJpaRepository, modelMapper),
                new OperationRepositoryPortImpl(operationJpaRepository, barbershopRepositoryPort, modelMapper)
        );
    }

    @Bean
    public UserService userService(UserJpaRepository userJpaRepository,
                                   PermissionGroupRepositoryPortImpl permissionGroupRepositoryPort,
                                   PasswordEncoder passwordEncoder,
                                   ModelMapper modelMapper) {
        return new UserService(new UserRepositoryPortImpl(userJpaRepository, permissionGroupRepositoryPort, passwordEncoder, modelMapper));
    }

    @Bean
    public AddressService addressService(AddressJpaRepository addressJpaRepository,
                                         CityRepositoryPortImpl cityRepositoryPort,
                                         ModelMapper modelMapper) {
        return new AddressService(new AddressRepositoryPortImpl(addressJpaRepository, cityRepositoryPort, modelMapper));
    }

    @Bean
    public BarbershopService barbershopService(BarbershopJpaRepository barbershopJpaRepository,
                                               AddressRepositoryPortImpl addressRepositoryPort,
                                               UserRepositoryPortImpl userRepositoryPort,
                                               ModelMapper modelMapper) {
        return new BarbershopService(
                new BarbershopRepositoryPortImpl(barbershopJpaRepository, addressRepositoryPort, userRepositoryPort, modelMapper),
                new BarbershopRepositoryFindAllImpl(barbershopJpaRepository, modelMapper)
        );
    }

    @Bean
    public CityService cityService(CityJpaRepository cityJpaRepository,
                                   StateRepositoryPortImpl stateRepositoryPort,
                                   ModelMapper modelMapper) {
        return new CityService(
                new CityRepositoryPortImpl(cityJpaRepository, stateRepositoryPort, modelMapper),
                new CityRepositoryFindAllImpl(cityJpaRepository, modelMapper)
        );
    }

    @Bean
    public StateService stateService(StateJpaRepository stateJpaRepository,
                                     ModelMapper modelMapper) {
        return new StateService(
                new StateRepositoryPortImpl(stateJpaRepository, modelMapper),
                new StateRepositoryFindAllImpl(stateJpaRepository, modelMapper)
        );
    }

}
