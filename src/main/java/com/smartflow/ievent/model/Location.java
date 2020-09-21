package com.smartflow.ievent.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Location", schema = "core")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer Id;
    private String LocationNumber;
    private String Description;
    private Integer AreaId;
    private Double X;
    private Double Y;
    private Double Z;
    private Integer State;
    private Integer CreatorId;
    private Date CreateDateTime;
    private Integer EditorId;
    private Date EditDateTime;
    private Integer Type;
}
