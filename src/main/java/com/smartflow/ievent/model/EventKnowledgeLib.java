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
@Table(name = "Event_KnowledgeLib", schema = "ievent", catalog = "MESDB")
public class EventKnowledgeLib {
    @Id
    @GeneratedValue
    private Integer Id;
    private Date CreationDateTime;

}
