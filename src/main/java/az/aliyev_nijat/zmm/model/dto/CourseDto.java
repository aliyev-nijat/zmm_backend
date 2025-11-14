package az.aliyev_nijat.zmm.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseDto {

    @Null
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    @NotNull
    @NotBlank
    @NotEmpty
    private String about;
    private String imageUrl;
    private Long imageId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String teacherFirstName;

    @NotNull
    @NotBlank
    @NotEmpty
    private String teacherLastName;

    @NotNull
    private String teacherAbout;
    private String teacherImageUrl;
    private Long teacherImageId;
}
