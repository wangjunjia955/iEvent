package com.smartflow.ievent.model;

import lombok.*;

import java.util.Date;

import javax.persistence.*;

/**
 * 用户表
 *
 * @author admin
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "[User]", schema = "core")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String UserName;
    private String Password;
    private Integer PlatformId;
    private String EmailAddress;
    private String Phone;
    private Date CreationDateTime;
    private Integer CreatorId;
    private Date EditDateTime;
    private Integer EditorId;
    private Integer State;
    private Date LastLoginTime;
    private String Account;
    private String UserCode;

}
