package com.smartflow.ievent.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EventEvaluationRecord", schema = "ievent")
public class EventEvaluationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventId")
    private Event Event;
    private Integer EvaluateGrade;
    private String EvaluateComment;
    private Date CreationDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId")
    private User User;
    private Integer State;
}
