package com.igortullio.barber.core.pageable;

import com.igortullio.barber.core.exception.BarberException;

public class PageableBarber {

    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;

    public PageableBarber(Integer page, Integer size, Integer totalPages, Long totalElements) {
        this.setPage(page);
        this.setSize(size);
        this.setTotalPages(totalPages);
        this.setTotalElements(totalElements);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page < 0) throw new BarberException("Page index must not be less than zero");
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        if (size < 1) throw new BarberException("Page size must not be less than one");
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

}
