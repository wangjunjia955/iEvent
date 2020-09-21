package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EditEventCatetoryInputDTO {
    @NotNull(message = "{eventCategory.Id.invalid}")
    private Integer Id;
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
