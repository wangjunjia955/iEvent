package com.smartflow.ievent.dto.MyEvent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@ApiModel(value = "我的任务查询条件对象")
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class MyEventInputForSearch {
    @ApiModelProperty(value = "用户ID", name = "UserId")
    private Integer UserId;
    @ApiModelProperty(value = "开始时间")
    private String StartDateTime;//开始时间
    @ApiModelProperty(value = "结束时间")
    private String EndDateTime;//结束时间
    @ApiModelProperty(value = "No")
    private String No;//No
    @ApiModelProperty(value = "区域")
    private Integer AreaId;//区域
    @ApiModelProperty(value = "站点")
    private Integer StationId;//站点
    @ApiModelProperty(value = "状态")
    private Integer State;//状态
    @ApiModelProperty(value = "当前页数")
    @NotNull(message = "{condition.pageIndex.invalid}")
    private Integer PageIndex;
    @ApiModelProperty(value = "每页显示的条数")
    @NotNull(message = "{condition.pageSize.invalid}")
    private Integer PageSize;
}
