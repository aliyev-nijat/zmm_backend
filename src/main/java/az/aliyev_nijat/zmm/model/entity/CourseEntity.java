package az.aliyev_nijat.zmm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CourseEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String about;

    private String imagePath;

    @Column(nullable = false)
    private String teacherFirstName;

    @Column(nullable = false)
    private String teacherLastName;

    @Column(nullable = false)
    private String teacherAbout;
    private String teacherImagePath;
}
