package com.smartflow.ievent.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

/**
 * 工作站实体
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Station", schema = "core")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer Id;
    @Column(unique = true, nullable = false)
    private String StationNumber;
    @Column(nullable = false)
    private String Name;
    private Date CreationDateTime;
    private Date EditDateTime;
    private Integer EditorId;
    @Column(nullable = false)
    private Integer StationType;
    @Column(nullable = false)
    private Integer State;
    private Integer FactoryId;
    private Integer CreatorId;

    public Station(Integer id, String stationNumber, String name) {
        Id = id;
        StationNumber = stationNumber;
        Name = name;
    }

    public Station(Integer id, String stationNumber) {
        Id = id;
        StationNumber = stationNumber;
    }
}
