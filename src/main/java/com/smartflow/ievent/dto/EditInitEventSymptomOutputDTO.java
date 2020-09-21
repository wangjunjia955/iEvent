package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EditInitEventSymptomOutputDTO {
    private Integer Id;
    private String SymptomCode;
    private String Name;
    private String Description;
    private String ImpactDegree;
    private String UrgencyDegree;
}
