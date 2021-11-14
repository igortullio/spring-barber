package com.igortullio.barber.adapter.specifications;

import com.igortullio.barber.adapter.controller.param.ScheduleParam;
import com.igortullio.barber.adapter.database.entity.AddressEntity;
import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.core.exception.BarberException;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class SpecificationTemplate {

    public static Specification<CityEntity> cityStateId(final Long stateId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CityEntity, StateEntity> join = root.join("state");

            return cb.equal(join.get("id"), stateId);
        };
    }

    public static Specification<BarbershopEntity> barbershopCityId(final Long cityId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<BarbershopEntity, AddressEntity> joinAddress = root.join("address");
            Join<AddressEntity, CityEntity> joinCity = joinAddress.join("city");

            return cb.equal(joinCity.get("id"), cityId);
        };
    }

    public static Specification<OperationEntity> operationBarbershopId(Long barbershopId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<OperationEntity, BarbershopEntity> joinBarbershop = root.join("barbershop");

            return cb.equal(joinBarbershop.get("id"), barbershopId);
        };
    }

    public static Specification<ScheduleEntity> scheduleParam(ScheduleParam scheduleParam) {
        if (Objects.isNull(scheduleParam.getUserId()) && Objects.isNull(scheduleParam.getOperationId())) {
            throw new BarberException("Get in /schedules must have parameters (" + scheduleParam.toString() + ")");
        }

        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicateList = new ArrayList<>();

            if (Objects.nonNull(scheduleParam.getUserId())) {
                Join<ScheduleEntity, UserEntity> joinUser = root.join("user");
                predicateList.add(cb.equal(joinUser.get("id"), scheduleParam.getUserId()));
            }

            if (Objects.nonNull(scheduleParam.getOperationId())) {
                Join<ScheduleEntity, OperationEntity> joinOperation = root.join("operation");
                predicateList.add(cb.equal(joinOperation.get("id"), scheduleParam.getOperationId()));
            }

            Predicate[] predicates = predicateList.toArray(new Predicate[0]);
            return cb.and(predicates);
        };
    }

}
