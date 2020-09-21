package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class MaintenanceAnalysisConditionInputDTO {
    private String StartDateTime;
    private String EndDateTime;
    private Integer AreaId;
    private List<Integer> StationIdList;
}
