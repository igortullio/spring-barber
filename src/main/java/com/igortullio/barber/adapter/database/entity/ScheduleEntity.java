package com.igortullio.barber.adapter.database.entity;

import com.igortullio.barber.adapter.database.entity.enumeration.ScheduleStatusEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "schedule")
public class ScheduleEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbershop_id")
    @ToString.Exclude
    private BarbershopEntity barbershop;

    @Column(nullable = false)
    private OffsetDateTime date;

    @Enumerated(EnumType.STRING)
    private ScheduleStatusEntity status;

}
