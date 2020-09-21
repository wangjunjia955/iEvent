package com.smartflow.ievent.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EventEscalationProcess", schema = "ievent")
public class EventEscalationProcess {
    @Id
    @GeneratedValue
    private Integer Id;
    private String EscalationProcessCode;
    private String Name;

}
