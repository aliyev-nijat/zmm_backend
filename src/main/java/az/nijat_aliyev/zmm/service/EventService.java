package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.exception.EventNotFoundException;
import az.nijat_aliyev.zmm.mapper.EventMapper;
import az.nijat_aliyev.zmm.model.dto.EventDto;
import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.model.entity.ImageEntity;
import az.nijat_aliyev.zmm.repository.EventRepository;
import az.nijat_aliyev.zmm.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventMapper mapper;

    @Autowired
    private final EventRepository repository;
    private final ImageRepository imageRepository;

    public EventDto create(EventDto event) {
        return mapper
                .map(
                        repository
                                .create(
                                        mapper.map(event)
                                )
                );
    }

    public List<EventDto> findAll() {
        return repository.findAll()
                .stream()
                .map(entity -> mapper.map(entity))
                .toList();
    }

    public EventDto getById(@NonNull Long id) {
        EventEntity event = repository.getById(id);
        if (event == null) {
            throw new EventNotFoundException("Event not found with given id");
        }

        return mapper.map(event);
    }

    public void deleteById(Long id) {
        deleteImage(id);
        repository.delete(id);
    }

    public EventDto update(@NonNull Long id, EventDto event)
            throws EventNotFoundException {
        EventEntity oldEvent = repository.getById(id);
        if (oldEvent == null) {
            throw new EventNotFoundException(
                    String.format("Event not found with given id(%d).", id)
            );
        }
        event.setId(id);
        event.setImageUrl(oldEvent.getImageUrl());
        event.setImageId(oldEvent.getImageId());
        return mapper
                .map(
                        repository.update(
                                mapper.map(event)
                        )
                );
    }

    public Map<String, Object> uploadImage(
            Long eventId,
            MultipartFile image
    ) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        EventEntity event = repository.getById(eventId);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Long oldImageId = event.getImageId();
        if (oldImageId != null) {
            imageRepository.delete(oldImageId);
        }
        String extension = image
                .getOriginalFilename()
                .split("\\.")[1];
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
        event.setImageId(newImageId);
        event.setImageUrl(String.format(
                "/api/images/%d",
                newImageId
        ));
        repository.update(event);
        Map<String, Object> result = new HashMap<>();
        result.put("imageId", newImageId);
        result.put("imageUrl", String.format("/images/%d", newImageId));

        return result;
    }

    public void deleteImage(Long eventId) {
        EventEntity event = repository.getById(eventId);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Long imageId = event.getImageId();
        if (imageId != null) {
            imageRepository.delete(imageId);
            event.setImageUrl(null);
            event.setImageId(null);
            repository.update(event);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image already not exists"
            );
        }

    }
}
