package com.igortullio.barber.core.port;

import com.igortullio.barber.core.domain.AbstractDomain;
import com.igortullio.barber.core.pageable.PageBarber;
import com.igortullio.barber.core.pageable.PageableBarber;

public interface RepositoryFindAllPort<T extends AbstractDomain> {

    PageBarber<T> findAll(PageableBarber pageableBarber);

}
