package az.aliyev_nijat.zmm.model.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseApplyDto {

    @NotNull
    private String firstName;
    private String lastName;

    @Min(1850)
    @Max(2500)
    private Integer birthYear;
    private String contactNumber;
    private String gender;
}
