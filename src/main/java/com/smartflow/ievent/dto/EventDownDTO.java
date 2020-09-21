package com.smartflow.ievent.dto;

import lombok.Data;

@Data
public class EventDownDTO {
    private Integer StationId;
    private String StationName;
    private Integer DownTime;
//    private Integer EventCategoryId;
//    private String EventCategoryName;
}
