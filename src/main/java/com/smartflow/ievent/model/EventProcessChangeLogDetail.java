package com.smartflow.ievent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EventProcessChangeLogDetail", schema = "ievent")
public class EventProcessChangeLogDetail {
    @Id
    @GeneratedValue
    private Integer Id;
    private String Property;
    private String OriginalValue;
    private String FinalValue;

}
