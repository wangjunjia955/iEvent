package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class MySubscriptionListConditionOutputDTO {
    private Integer Id;
    private String EventCategory;
    private String EventSymptom;
    private String ImpactDegree;
    private String UrgencyDegree;
    private String Subscriber;
    private String SubscribeRole;
}
