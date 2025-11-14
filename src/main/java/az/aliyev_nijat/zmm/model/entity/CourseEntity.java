package az.aliyev_nijat.zmm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String about;

    private String imageUrl;
    private Long imageId;

    @Column(nullable = false)
    private String teacherFirstName;

    @Column(nullable = false)
    private String teacherLastName;

    @Column(nullable = false)
    @Lob
    private String teacherAbout;
    private String teacherImageUrl;
    private Long teacherImageId;
}
