package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.omg.CORBA.Any;

import java.util.Date;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventForListOutputDTO {
    private Integer Id;
    private String No;
    private String AreaNumber;
    private String StationNumber;
    private String Symptom;
    private String UrgencyDegree;
    private String ImpactDegree;
    private String Title;
    private String ReportUser;
    private String Assignee;
    private Date ReportDateTime;
    private Date IssueDateTime;
    private String PassedTime;
    private Date AssignedDateTime;
    private Date ActionStartedDateTime;
    private Date ActionFinishedDateTime;
    private Date SignedOffDateTime;
    private String State;
    private String EventColor;
}
