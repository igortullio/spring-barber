package com.igortullio.barber.core.exception.in_use;

import com.igortullio.barber.core.domain.Schedule;

public class ScheduleInUseException extends AbstractInUseException {

    public ScheduleInUseException(Long id) {
        super(Schedule.class, id);
    }

}
