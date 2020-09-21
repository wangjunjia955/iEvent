package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.smartflow.ievent.model.EventCatetory;
import com.smartflow.ievent.model.EventEscalationProcess;
import com.smartflow.ievent.model.Role;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class AddEventCatetoryInputDTO {
    @NotBlank(message = "{eventCategory.CategoryCode.invalid}")
    @Size(max = 50, message = "{eventCategory.CategoryCode.lengthLimit}")
    private String CategoryCode;
    @NotBlank(message = "{eventCategory.Name.invalid}")
    @Size(max = 50, message = "{eventCategory.Name.lengthLimit}")
    private String Name;
    @Size(max = 200, message = "{eventCategory.Description.lengthLimit}")
    private String Description;
    private Integer RoleId;
    private List<Integer> ParentCategoryId;
}
