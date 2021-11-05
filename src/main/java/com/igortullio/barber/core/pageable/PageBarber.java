package com.igortullio.barber.core.pageable;

import com.igortullio.barber.core.domain.AbstractDomain;

import java.util.List;

public class PageBarber<T extends AbstractDomain> {

    private List<T> list;
    private PageableBarber pageableBarber;

    public PageBarber(List<T> list, PageableBarber pageableBarber) {
        this.list = list;
        this.pageableBarber = pageableBarber;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageableBarber getPageable() {
        return pageableBarber;
    }

    public void setPageable(PageableBarber pageableBarber) {
        this.pageableBarber = pageableBarber;
    }

}
