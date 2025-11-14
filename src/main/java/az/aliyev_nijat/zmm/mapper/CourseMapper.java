package az.aliyev_nijat.zmm.mapper;

import az.aliyev_nijat.zmm.model.dto.CourseDto;
import az.aliyev_nijat.zmm.model.entity.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseEntity map(final CourseDto source) {
        CourseEntity target = new CourseEntity();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setImageUrl(source.getImageUrl());
        target.setTeacherFirstName(source.getTeacherFirstName());
        target.setTeacherLastName(source.getTeacherLastName());
        target.setTeacherAbout(source.getTeacherAbout());
        target.setTeacherImageUrl(source.getTeacherImageUrl());
        target.setImageId(source.getImageId());
        target.setTeacherImageId(source.getTeacherImageId());

        return target;
    }

    public CourseDto map(final CourseEntity source) {
        CourseDto target = new CourseDto();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setImageUrl(source.getImageUrl());
        target.setTeacherFirstName(source.getTeacherFirstName());
        target.setTeacherLastName(source.getTeacherLastName());
        target.setTeacherAbout(source.getTeacherAbout());
        target.setTeacherImageUrl(source.getTeacherImageUrl());
        target.setImageId(source.getImageId());
        target.setTeacherImageId(source.getTeacherImageId());

        return target;
    }
}
