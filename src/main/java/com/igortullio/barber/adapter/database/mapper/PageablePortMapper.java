package com.igortullio.barber.adapter.database.mapper;

import com.igortullio.barber.core.pageable.PageableBarber;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageablePortMapper {

    public static PageableBarber of(Page<?> page) {
        PageableBarber pageableBarber = of(page.getPageable());
        pageableBarber.setTotalPages(page.getTotalPages());
        pageableBarber.setTotalElements(pageableBarber.getTotalElements());

        return pageableBarber;
    }

    public static PageableBarber of(Pageable pageable) {
        return new PageableBarber(pageable.getPageNumber(), pageable.getPageSize(), null, null);
    }

}
