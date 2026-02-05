package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.mapper.CourseMapper;
import az.aliyev_nijat.zmm.model.dto.CourseApplyDto;
import az.aliyev_nijat.zmm.model.dto.CourseDto;
import az.aliyev_nijat.zmm.model.entity.CourseEntity;
import az.aliyev_nijat.zmm.repository.CourseRepository;
import az.aliyev_nijat.zmm.util.TelegramAdapter;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final ImageService imageService;
    private final CourseMapper mapper;

    public CourseDto getById(Long id) {
        return repository
                .findById(id)
                .map(mapper::map)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    public CourseDto create(CourseDto course) {
        return mapper.map(
                repository.save(
                        mapper.map(course)
                )
        );
    }

    public void deleteById(@NonNull Long id) {
        CourseEntity course = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ));
        if (course.getImageId() != null) {
            imageService.deleteById(course.getImageId());
        }
        if (course.getTeacherImageId() != null) {
            imageService.deleteById(course.getTeacherImageId());
        }
        repository.deleteById(id);
    }

    public CourseDto update(Long id, CourseDto course) {
        course.setId(id);
        CourseEntity oldCourse = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND
                        )
                );
        CourseEntity newCourse = mapper.map(course);
        newCourse.setImageId(oldCourse.getImageId());
        newCourse.setImageUrl(oldCourse.getImageUrl());
        newCourse.setTeacherImageId(oldCourse.getTeacherImageId());
        newCourse.setTeacherImageUrl(
                oldCourse.getTeacherImageUrl()
        );

        return mapper.map(repository.save(newCourse));
    }

    public Map<String, Object> uploadImage(
            @NonNull Long courseId,
            MultipartFile image
    ) {
            CourseEntity course = repository
                    .findById(courseId)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND)
                    );
            Long oldImageId = course.getImageId();
            if (oldImageId != null) {
                imageService.deleteById(oldImageId);
            }
            Long newImageId = imageService.create(image);
            course.setImageId(newImageId);
            course.setImageUrl(String.format(
                    "/api/images/%d",
                    newImageId
            ));
            repository.save(course);
            Map<String, Object> result = new HashMap<>();
            result.put("imageId", newImageId);
            result.put("imageUrl", String.format("/images/%d", newImageId));

            return result;
    }

    public void deleteImage(@NonNull Long courseId) {
        CourseEntity course = repository
                .findById(courseId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        Long imageId = course.getImageId();
        if (imageId != null) {
            imageService.deleteById(imageId);
            course.setImageUrl(null);
            course.setImageId(null);
            repository.save(course);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image already not exists"
            );
        }
    }

    @Transactional
    public Map<String, Object> uploadTeacherImage(
            @NonNull Long courseId,
            MultipartFile image
    ) {
            CourseEntity course = repository
                    .findById(courseId)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND)
                    );
            Long oldTeacherImageId = course.getTeacherImageId();
            if (oldTeacherImageId != null) {
                imageService.deleteById(oldTeacherImageId);
            }
            Long newTeacherImageId = imageService.create(image);
            course.setTeacherImageId(newTeacherImageId);
            course.setTeacherImageUrl(String.format(
                    "/api/images/%d",
                    newTeacherImageId
            ));
            repository.save(course);
            Map<String, Object> result = new HashMap<>();
            result.put("imageId", newTeacherImageId);
            result.put("imageUrl", String.format("/images/%d", newTeacherImageId));

            return result;
    }

    public void deleteTeacherImage(@NonNull Long courseId) {
        CourseEntity course = repository
                .findById(courseId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        Long teacherImageId = course.getTeacherImageId();
        if (teacherImageId != null) {
            imageService.deleteById(teacherImageId);
            course.setTeacherImageUrl(null);
            course.setTeacherImageId(null);
            repository.save(course);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image already not exists"
            );
        }
    }

    public List<CourseDto> getAll() {
        List<CourseDto> result = new LinkedList<>();
        repository
                .findAll()
                .stream()
                .map(mapper::map)
                .forEach(result::add);
        Collections.shuffle(result);

        return result;
    }

    public ResponseEntity<Void> apply(
            @PathVariable Long courseId,
            @RequestBody CourseApplyDto body
    ) {
        TelegramAdapter tg = new TelegramAdapter(
                Dotenv.load().get("TG_BOT_TOKEN"),
                Dotenv.load().get("TG_BOT_CHATID")
        );
        String courseName = repository
                .findById(courseId)
                .map(CourseEntity::getTitle)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Course not found by given id"
                        )
                );
        String message = String.format("""
                        %s kursuna yeni qeydiyyat:
                        Ad: %s,
                        Soyad: %s,
                        Təvəllüd: %d,
                        Əlaqə nömrəsi: %s,
                        Cins: %s""",
                courseName,
                body.getFirstName() == null ? "" : body.getFirstName(),
                body.getLastName() == null ? "" : body.getLastName(),
                body.getBirthYear() == null ? "" : body.getBirthYear().intValue(),
                body.getContactNumber() == null ? "" : body.getContactNumber(),
                body.getGender() == null ? "" : body.getGender()
        );
        tg.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
