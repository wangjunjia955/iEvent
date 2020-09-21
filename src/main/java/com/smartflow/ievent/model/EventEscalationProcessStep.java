package com.smartflow.ievent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EventEscalationProcessStep", schema = "ievent")
public class EventEscalationProcessStep {
    @Id
    @GeneratedValue
    private Integer Id;
    private Integer Sequence;
    private BigDecimal TimeThresholdInSecond;
}
