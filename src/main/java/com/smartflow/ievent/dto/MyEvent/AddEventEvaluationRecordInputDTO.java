package com.smartflow.ievent.dto.MyEvent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AddEventEvaluationRecordInputDTO {
    @NotNull(message = "{event.Id.invalid}")
    private Integer EventId;
    @NotNull(message = "{condition.userId.invalid}")
    private Integer UserId;
    private boolean IsSolved;
    private Integer Grade;
    private String Comment;

    public boolean getIsSolved() {
        return IsSolved;
    }
}
