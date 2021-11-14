package com.igortullio.barber.adapter.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igortullio.barber.core.domain.enumeration.ScheduleStatus;
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

    @Column(nullable = false)
    private OffsetDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    @ToString.Exclude
    private OperationEntity operation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

}
