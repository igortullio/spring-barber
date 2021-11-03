package com.igortullio.barber.core.port;

import com.igortullio.barber.core.domain.AbstractDomain;

public interface RepositoryPort<T extends AbstractDomain> {

    T find(Long id);

    T save(T t);

    T update(Long id, T t);

    void delete(Long id);

}
