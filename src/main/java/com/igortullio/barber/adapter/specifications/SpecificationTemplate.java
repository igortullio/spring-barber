package com.igortullio.barber.adapter.specifications;

import com.igortullio.barber.adapter.database.entity.AddressEntity;
import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.entity.PermissionEntity;
import com.igortullio.barber.adapter.database.entity.PermissionGroupEntity;
import com.igortullio.barber.adapter.database.entity.ScheduleEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.util.SecurityUtil;
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

    public static Specification<ScheduleEntity> scheduleParam(Long operationId) {
        UserEntity userLogged = SecurityUtil.getUserLogged();

        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicateList = new ArrayList<>();

            if (Objects.isNull(operationId)) {
                Join<ScheduleEntity, UserEntity> joinUser = root.join("user");
                predicateList.add(cb.equal(joinUser.get("id"), userLogged.getId()));
            } else {
                Join<ScheduleEntity, OperationEntity> joinOperation = root.join("operation");
                predicateList.add(cb.equal(joinOperation.get("id"), operationId));
            }

            Predicate[] predicates = predicateList.toArray(new Predicate[0]);
            return cb.and(predicates);
        };
    }

    public static Specification<PermissionGroupEntity> permissionGroupPermissionId(Long permissionId) {
        if (Objects.isNull(permissionId)) {
            return null;
        }

        return (root, query, cb) -> {
            query.distinct(true);
            Join<PermissionGroupEntity, PermissionEntity> joinPermission = root.join("permissionSet");

            return cb.equal(joinPermission.get("id"), permissionId);
        };
    }

}
