package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventSymptomListOutputDTO {
    private Integer Id;
    private String SymptomCode;
    private String Name;
    private String Description;
    private String DefaultImpactDegree;
    private String DefaultUrgencyDegree;
}
