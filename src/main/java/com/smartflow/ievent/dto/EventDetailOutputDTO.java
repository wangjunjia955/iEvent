package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventDetailOutputDTO {
    private String No;
    private String Area;
    private String Station;
    private String Symptom;
    private String Description;
    private String EventCategory;
    private String ImpactDegree;
    private String UrgencyDegree;
    private Date ReportDateTime;
    private String ReportUser;
//    private Date ActionStartedDateTime;
    private Date ActionFinishedDateTime;
    private String ActionOwner;
    private String Clarify;
    private String Solution;
    private boolean IsSolved;

    public boolean getIsSolved() {
        return IsSolved;
    }
}
