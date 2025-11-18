package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.model.dto.CourseApplyDto;
import az.aliyev_nijat.zmm.model.dto.CourseDto;
import az.aliyev_nijat.zmm.util.TelegramAdapter;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import az.aliyev_nijat.zmm.service.CourseService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseDto> create(
            @Valid @RequestBody CourseDto course
    ) {
        return ResponseEntity.ok(service.create(course));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CourseDto course
    ) {
        return ResponseEntity.ok(service.update(id, course));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{courseId}/image")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @PathVariable Long courseId,
            @RequestParam MultipartFile image
    ) {
        return ResponseEntity.ok(service.uploadImage(courseId, image));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{courseId}/image")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long courseId
    ) {
        service.deleteImage(courseId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{courseId}/teacher/image")
    public ResponseEntity<Map<String, Object>> uploadTeacherImage(
            @PathVariable Long courseId,
            @RequestParam MultipartFile image
    ) {
        return ResponseEntity.ok(service.uploadTeacherImage(courseId, image));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{courseId}/teacher/image")
    public ResponseEntity<Void> deleteTeacherImage(
            @PathVariable Long courseId
    ) {
        service.deleteTeacherImage(courseId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/apply/{courseId}")
    public ResponseEntity<Void> apply(
            @PathVariable Long courseId,
            @RequestBody CourseApplyDto body
            )
    {
        service.apply(courseId,body);
        log.info(courseId + body.toString());
        return ResponseEntity.noContent().build();
    }
}
