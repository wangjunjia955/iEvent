package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EditEventSubscriptionInitOutputDTO {
    private Integer Id;
    private List<Integer> EventCategoryId;
    private Integer EventSymptomId;
    private Integer ImpactDegree;
    private Integer UrgencyDegree;
    private Integer SubscribeRoleId;
}
