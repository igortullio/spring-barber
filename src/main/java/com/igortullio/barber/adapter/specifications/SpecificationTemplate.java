package com.igortullio.barber.adapter.specifications;

import com.igortullio.barber.adapter.database.entity.AddressEntity;
import com.igortullio.barber.adapter.database.entity.BarbershopEntity;
import com.igortullio.barber.adapter.database.entity.CityEntity;
import com.igortullio.barber.adapter.database.entity.OperationEntity;
import com.igortullio.barber.adapter.database.entity.StateEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

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

}
