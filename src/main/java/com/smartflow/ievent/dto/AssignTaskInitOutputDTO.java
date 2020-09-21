package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AssignTaskInitOutputDTO {
    private Integer EventId;
    private String No;
    private String Area;
    private String Station;
    private String Title;
    private String Description;
//    private String Symptom;
    private String EventCategory;
    private String ImpactDegree;
    private String UrgencyDegree;
    //private List<Map<String,Object>> RoleUserList;
}
