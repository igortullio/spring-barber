package com.igortullio.barber.core.port;

public interface RepositorySchedulePort {

    void confirm(Long id);

    void cancel(Long id);

}
