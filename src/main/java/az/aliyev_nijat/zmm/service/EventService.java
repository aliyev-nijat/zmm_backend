package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.exception.EventNotFoundException;
import az.aliyev_nijat.zmm.mapper.EventMapper;
import az.aliyev_nijat.zmm.model.dto.EventDto;
import az.aliyev_nijat.zmm.model.entity.EventEntity;
import az.aliyev_nijat.zmm.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventMapper mapper;
    private final EventRepository repository;
    private final ImageService imageService;

    public EventDto create(EventDto event) {
        EventEntity eventEntity = mapper.map(event);
        eventEntity.setId(null);
        return mapper
                .map(
                        repository
                                .save(
                                        eventEntity
                                )
                );
    }

    public List<EventDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .sorted(
                        Comparator.comparing(
                                EventDto::getOrder,
                                Comparator.nullsLast(Long::compareTo)
                        )
                )
                .toList();
    }

    public EventDto getById(@NonNull Long id) {
        EventEntity event = repository
                .findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found with given id")
                );

        return mapper.map(event);
    }

    public void deleteById(@NonNull Long id) {
        repository.findById(id)
                .filter(event -> event.getImageId() != null)
                .map(EventEntity::getImageId)
                .ifPresent(this::deleteImage);
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public EventDto update(@NonNull Long id, EventDto event)
            throws EventNotFoundException {
        EventEntity oldEvent = repository
                .findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(String.format(
                                "Event not found with given id(%d).",
                                id
                        ))
                );
        event.setId(id);
        event.setImageUrl(oldEvent.getImageUrl());
        event.setImageId(oldEvent.getImageId());
        return mapper
                .map(
                        repository.save(
                                mapper.map(event)
                        )
                );
    }

    @Transactional
    public Map<String, Object> uploadImage(
            @NonNull Long eventId,
            MultipartFile image
    ) {
        EventEntity event = repository
                .findById(eventId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        Long oldImageId = event.getImageId();
        if (oldImageId != null) {
            imageService.deleteById(oldImageId);
        }
        Long newImageId = imageService.create(image);
        event.setImageId(newImageId);
        event.setImageUrl(String.format(
                "/api/images/%d",
                newImageId
        ));
        repository.save(event);
        Map<String, Object> result = new HashMap<>();
        result.put("imageId", newImageId);
        result.put("imageUrl", String.format("/images/%d", newImageId));

        return result;
    }

    public void deleteImage(@NonNull Long eventId) {
        EventEntity event = repository
                .findById(eventId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        Long imageId = event.getImageId();
        if (imageId != null) {
            imageService.deleteById(imageId);
            event.setImageUrl(null);
            event.setImageId(null);
            repository.save(event);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image already not exists"
            );
        }
    }
}
