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
@Table(name = "EventKnowledgeLibrary", schema = "ievent", catalog = "MESDB")
public class EventKnowledgeLibrary {
    @Id
    private Integer Id;
    private String KnowledgeCode;
    private Integer Type;
    private String Name;
    private String Problem;
    private String Solution;
    private Date CreationDateTime;
    @ManyToOne
    @JoinColumn(name = "CreatorId")
    private User User;
    private Integer State;

}
