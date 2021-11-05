package com.igortullio.barber.adapter.specifications;

import com.igortullio.barber.adapter.database.entity.CityEntity;
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

}
