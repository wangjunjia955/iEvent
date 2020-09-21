package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventConcernedListInputDTO {
    @NotNull(message = "{condition.userId.required}")
    private Integer UserId;
    @NotNull(message = "{condition.pageIndex.required}")
    private Integer PageIndex;
    @NotNull(message = "{condition.pageSize.required}")
    private Integer PageSize;
}
