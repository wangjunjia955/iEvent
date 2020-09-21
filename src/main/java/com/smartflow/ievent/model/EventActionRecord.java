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
@Table(name = "EventActionRecord", schema = "ievent")
public class EventActionRecord {
    @Id
    @GeneratedValue
    private Integer Id;
    private Date CreationDateTime;
    private String ActionDetail;
}
