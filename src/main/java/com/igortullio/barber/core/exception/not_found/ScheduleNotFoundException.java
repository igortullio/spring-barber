package com.igortullio.barber.core.exception.not_found;

import com.igortullio.barber.core.domain.Schedule;

public class ScheduleNotFoundException extends AbstractNotFoundException {

    public ScheduleNotFoundException(Long id) {
        super(Schedule.class, id);
    }

}
