package com.igortullio.barber.core.domain;

import com.igortullio.barber.core.domain.enumeration.ScheduleStatus;

import java.time.OffsetDateTime;

public class Schedule extends AbstractDomain {

    private OffsetDateTime date;
    private ScheduleStatus status;
    private Barbershop barbershop;
    private User user;

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public Barbershop getBarbershop() {
        return barbershop;
    }

    public void setBarbershop(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
