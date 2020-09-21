package com.smartflow.ievent.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工厂实体
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Factory", schema = "core")
public class Factory {
    @Id
    @GeneratedValue
    private Integer Id;
    private String FactoryCode;
    private String Name;
    private String Description;
    private Integer CompanyId;
    private Integer CreatorId;
    private Date CreationDateTime;
    private Integer EditorId;
    private Date EditDateTime;

    public Factory(Integer id, String factoryCode, String name) {
        Id = id;
        FactoryCode = factoryCode;
        Name = name;
    }
}
