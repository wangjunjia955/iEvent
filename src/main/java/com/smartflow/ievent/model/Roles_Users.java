package com.smartflow.ievent.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
@Entity
@Table(name="Roles_Users",schema = "core")
@Getter
@Setter
public class Roles_Users {
	@Id
	@GeneratedValue
	private Integer Id;
	private Integer RoleId;
	private Integer UserId;
	private Date CreationDateTime;
	private Integer CreatorId;
	
}
