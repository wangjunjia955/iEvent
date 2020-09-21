package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EditEventInputDTO {
    @NotNull(message="{event.Id.invalid}")
    private Integer EventId;
    @NotNull(message = "{eventSubscription.userId.invalid}")
    private Integer UserId;
    @NotEmpty(message = "{event.EventCategoryId.invalid}")
    private List<Integer> EventCategoryId;
    @NotNull(message="{event.ImpactDegree.invalid}")
    private Integer ImpactDegree;
    @NotNull(message="{event.UrgencyDegree.invalid}")
    private Integer UrgencyDegree;
    private String ActionStartedDateTime;
    private String ActionFinishedDateTime;
    private String Clarify;
    private String Solution;
    private boolean IsSolved;
}
