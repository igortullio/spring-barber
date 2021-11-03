package com.igortullio.barber.core.domain;

import java.time.OffsetDateTime;
import java.util.Objects;

public abstract class AbstractDomain {

    private Long id;
    private OffsetDateTime dateCreate;
    private OffsetDateTime dateUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getCreate() {
        return dateCreate;
    }

    public void setCreate(OffsetDateTime create) {
        this.dateCreate = create;
    }

    public OffsetDateTime getUpdate() {
        return dateUpdate;
    }

    public void setUpdate(OffsetDateTime update) {
        this.dateUpdate = update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractDomain that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
