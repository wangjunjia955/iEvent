package com.smartflow.ievent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EventProblemTag", schema = "ievent")
public class EventProblemTag {
    @Id
    @GeneratedValue
    private Integer Id;
    private String TagName;
}
