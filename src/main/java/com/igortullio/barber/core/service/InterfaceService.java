package com.igortullio.barber.core.service;

import com.igortullio.barber.core.domain.AbstractDomain;

public interface InterfaceService<T extends AbstractDomain> {

    T find(Long id);

    T save(T t);

    T update(Long id, T t);

    void delete(Long id);

}
