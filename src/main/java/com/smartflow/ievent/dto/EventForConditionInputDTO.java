package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventForConditionInputDTO {
    private String Area;
    private String StartDateTime;
    private String EndDateTime;
    private boolean DisplayIsOver;//显示已完结
    //private Integer SecondsToRefresh;//刷新间隔秒数
    @NotNull(message = "{condition.pageIndex.invalid}")
    private Integer PageIndex;
    @NotNull(message = "{condition.pageSize.invalid}")
    private Integer PageSize;

}
