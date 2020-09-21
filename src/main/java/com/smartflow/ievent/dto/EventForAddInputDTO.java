package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.smartflow.ievent.model.EventCatetory;
import com.smartflow.ievent.model.EventSymptom;
import com.smartflow.ievent.model.Station;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventForAddInputDTO {

    private Integer UserId;//当前登陆用户（事件报告人）
    @NotNull(message = "{event.AreaId.invalid}")
    private Integer AreaId;
    private Integer StationId;
    @NotNull(message = "{event.EventSymptomId.invalid}")
    private Integer EventSymptomId;
    @NotEmpty(message = "{event.EventCategoryId.invalid}")
    private List<Integer> EventCategoryId;
    @NotNull(message = "{event.ImpactDegree.invalid}")
    private Integer ImpactDegree;
    @NotNull(message = "{event.UrgencyDegree.invalid}")
    private Integer UrgencyDegree;
    @NotBlank(message = "{event.IssueDateTime.invalid}")
    private String IssueDateTime;
    @NotBlank(message = "{event.Title.invalid}")
    private String Title;
    @NotBlank(message = "{event.Description.invalid}")
    private String Description;

}
