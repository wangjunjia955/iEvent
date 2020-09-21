package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartflow.ievent.model.EventEscalationProcess;
import com.smartflow.ievent.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EventCategoryListOutputDTO {


    private Integer Id;

    private String CategoryCode;

    private String Name;

    private String Description;

    private String RoleName;

    private String ParentCategory;
}
