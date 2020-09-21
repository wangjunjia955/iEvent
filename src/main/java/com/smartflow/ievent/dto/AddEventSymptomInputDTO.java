package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AddEventSymptomInputDTO {
    @NotBlank(message = "{eventSymptom.SymptomCode.invalid}")
    @Size(max = 50, message = "{eventSymptom.SymptomCode.lengthLimit}")
    private String SymptomCode;
    @NotBlank(message = "{eventSymptom.Name.invalid}")
    @Size(max = 50, message = "{eventSymptom.Name.lengthLimit}")
    private String Name;
    @Size(max = 10, message = "{eventSymptom.Description.lengthLimit}")
    private String Description;
    private Integer ImpactDegree;
    private Integer UrgencyDegree;
}
