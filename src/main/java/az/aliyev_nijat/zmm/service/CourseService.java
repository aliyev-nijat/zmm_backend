package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.mapper.CourseMapper;
import az.aliyev_nijat.zmm.model.dto.CourseDto;
import az.aliyev_nijat.zmm.model.entity.CourseEntity;
import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import az.aliyev_nijat.zmm.repository.CourseRepository;
import az.aliyev_nijat.zmm.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final ImageRepository imageRepository;
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

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public CourseDto update(Long id, CourseDto course) {
        CourseEntity oldCourse = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND
                        )
                );
        CourseEntity newCourse = mapper.map(course);
        newCourse.setId(oldCourse.getId());
        newCourse.setImageUrl(oldCourse.getImageUrl());
        newCourse.setTeacherImageUrl(
                oldCourse.getTeacherImageUrl()
        );

        return mapper.map(repository.save(newCourse));
    }

    public Map<String, Object> uploadImage(
            @NonNull Long courseId,
            MultipartFile image
    ) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = repository
                .findById(courseId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        Long oldImageId = course.getImageId();
        if (oldImageId != null) {
            imageRepository.delete(oldImageId);
        }
        String[] splited = image.getOriginalFilename().split("\\.");
        String extension = splited[splited.length - 1];
        byte[] content;
        try {
            content = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setExtension(extension);
        imageEntity.setContent(content);
        ImageEntity newImageEntity = imageRepository.create(imageEntity);
        Long newImageId = newImageEntity.getId();
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
            imageRepository.delete(imageId);
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

    public Map<String, Object> uploadTeacherImage(
            @NonNull Long courseId,
            MultipartFile image
    ) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = repository
                .findById(courseId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        Long oldTeacherImageId = course.getTeacherImageId();
        if (oldTeacherImageId != null) {
            imageRepository.delete(oldTeacherImageId);
        }
        String[] splited = image.getOriginalFilename().split("\\.");
        String extension = splited[splited.length - 1];
        byte[] content;
        try {
            content = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setExtension(extension);
        imageEntity.setContent(content);
        ImageEntity newImageEntity = imageRepository.create(imageEntity);
        Long newTeacherImageId = newImageEntity.getId();
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
            imageRepository.delete(teacherImageId);
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
}
