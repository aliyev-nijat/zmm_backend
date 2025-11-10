package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.exception.EventNotFoundException;
import az.nijat_aliyev.zmm.mapper.EventMapper;
import az.nijat_aliyev.zmm.model.dto.EventDto;
import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.repository.EventRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventMapper mapper;

    @Autowired
    private final EventRepository repository;

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
        return mapper
                .map(
                        repository.update(
                                mapper.map(event)
                        )
                );
    }
}
