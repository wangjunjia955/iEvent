package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EditEventSubscriptionInputDTO {
    private Integer Id;
    @NotNull(message = "{eventSubscription.userId.invalid}")
    private Integer UserId;
    @NotEmpty(message = "{eventSubscription.eventCategoryId.invalid}")
    private List<Integer> EventCategoryId;
    private Integer EventSymptomId;
    private Integer ImpactDegree;
    private Integer UrgencyDegree;
    private Integer SubscribeRoleId;
}
