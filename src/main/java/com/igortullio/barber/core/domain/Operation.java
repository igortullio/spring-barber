package com.igortullio.barber.core.domain;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.util.Set;

public class Operation extends AbstractDomain {

    private DayOfWeek day;
    private OffsetTime openTime;
    private OffsetTime closeTime;
    private Barbershop barbershop;
    private Set<Schedule> scheduleSet;

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public OffsetTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(OffsetTime openTime) {
        this.openTime = openTime;
    }

    public OffsetTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(OffsetTime closeTime) {
        this.closeTime = closeTime;
    }

    public Barbershop getBarbershop() {
        return barbershop;
    }

    public void setBarbershop(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
    }

}
