package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.AbstractDomain;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;

public interface InterfaceFindAllService<T extends AbstractDomain> {

    PageBarber<T> findAll(Object o, PageableBarber pageableBarber);

}
