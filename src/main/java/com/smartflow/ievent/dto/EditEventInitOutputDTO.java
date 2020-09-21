package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EditEventInitOutputDTO {
    @NotNull(message="{event.Id.invalid}")
    private Integer EventId;
    @NotBlank(message="{event.No.invalid}")
    private String No;
    @NotNull(message="{event.AreaId.invalid}")
    private String Area;
    private String Station;
    @NotBlank(message="{event.Title.invalid}")
    private String Title;
    @NotBlank(message="{event.Description.invalid}")
    private String Description;
    @NotEmpty(message = "{event.EventCategoryId.invalid}")
    private List<Integer> EventCategoryId;
    @NotNull(message="{event.ImpactDegree.invalid}")
    private Integer ImpactDegree;
    @NotNull(message="{event.UrgencyDegree.invalid}")
    private Integer UrgencyDegree;
    private Date ActionStartedDateTime;
    private Date ActionFinishedDateTime;
    private String Clarify;
    private String Solution;
    private boolean IsSolved;

    public boolean getIsSolved() {
        return IsSolved;
    }
}
