package com.smartflow.ievent.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Area", schema = "core")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String AreaNumber;
    private String Description;
    private Integer FactoryId;
    private Integer CreatorId;
    private Date CreationDateTime;
    private Integer EditorId;
    private Date EditDateTime;
    private String Name;
    private Integer AreaType;
    private Integer State;


    public Area(Integer id, String areaNumber, String description) {
        Id = id;
        AreaNumber = areaNumber;
        Description = description;
    }
}
