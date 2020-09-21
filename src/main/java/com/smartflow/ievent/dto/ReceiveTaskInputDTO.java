package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ReceiveTaskInputDTO {
    @NotNull(message = "{condition.userId.required}")
    private Integer UserId;
    @NotEmpty(message = "{event.Id.required}")
    private List<Integer> EventIdList;
}
