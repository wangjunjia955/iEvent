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
@Table(name = "Role", schema = "core")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String RoleName;
    private Integer PlatformId;
    private Date CreationDateTime;
    private Integer CreatorId;
    private Date EditDateTime;
    private Integer EditorId;
    private Integer State;
    private Integer FactoryId;
    private String Visit;
    private String AreaIdList;

    public Role(Integer id, String roleName) {
        Id = id;
        RoleName = roleName;
    }
}
