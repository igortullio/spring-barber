package com.igortullio.barber.core.domain;

import com.igortullio.barber.core.domain.enumeration.ScheduleStatus;

import java.time.OffsetDateTime;

public class Schedule extends AbstractDomain {

    private OffsetDateTime dateTime;
    private ScheduleStatus status;
    private Operation operation;
    private User user;

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
