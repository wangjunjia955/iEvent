package com.smartflow.ievent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;


@Data
@NoArgsConstructor
@Entity
@Table(name = "EventProcessChangeLog", schema = "ievent")
public class EventProcessChangeLog {
    @Id
    @GeneratedValue
    private Integer Id;
    private Date ChangeDateTime;
    private Integer ChangerId;
    private Integer ChangeType;

}
