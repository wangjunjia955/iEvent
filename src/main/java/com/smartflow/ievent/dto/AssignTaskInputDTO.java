package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AssignTaskInputDTO {
    @NotNull(message = "{event.Id.required}")
    private Integer EventId;
    private Integer UserId;//分配人
    private Integer RoleId;
    @NotNull(message = "{condition.ActionOwnerId.required}")
    private Integer ActionOwnerId;//处理人
}
