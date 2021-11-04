package com.igortullio.barber.core.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class Operation extends AbstractDomain {

    private DayOfWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Barbershop barbershop;
    private Set<Schedule> scheduleSet;

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
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
