package com.smartflow.ievent.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Location_Station", schema = "core")
public class Location_Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer Id;
    @Column(nullable = false)
    private Integer LocationId;
    @Column(nullable = false)
    private Integer StationId;
}
