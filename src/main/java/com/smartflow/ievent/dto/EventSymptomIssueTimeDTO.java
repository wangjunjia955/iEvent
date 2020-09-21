package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventSymptomIssueTimeDTO {
    private Integer EventId;
    private Integer EventSymptomId;
    private String StationName;
    //private Integer EventCategoryId;
    //private String CategoryName;
    private Date IssueDateTime;
    private Date ActionStartedDateTime;
    private Date ActionFinishedDateTime;
}
