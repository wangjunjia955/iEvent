package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class PageConditionInputDTO {

    @NotNull(message = "{condition.pageIndex.invalid}")
    private Integer PageIndex;
    @NotNull(message = "{condition.pageSize.invalid}")
    private Integer PageSize;
}
