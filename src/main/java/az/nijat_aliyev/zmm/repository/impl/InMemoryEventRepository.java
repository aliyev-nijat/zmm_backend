package az.nijat_aliyev.zmm.repository.impl;

import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.repository.EventRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
//@Repository
public class InMemoryEventRepository implements EventRepository {

    private static List<EventEntity> events = new LinkedList<>();
    private static Long nextId = 1L;

    @Override
    public EventEntity create(EventEntity event) {
        event.setId(generateId());
        events.add(event);

        return event;
    }

    @Override
    public EventEntity getById(@NonNull Long id) {
        return events
                .stream()
                .filter(event -> id.equals(event.getId()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<EventEntity> findAll() {
        return List.copyOf(events);
    }

    @Override
    public EventEntity update(@NonNull EventEntity event) {
        if (event.getId() == null) return null;

        return events
                .stream()
                .filter(e ->
                        event.getId()
                                .equals(e.getId())
                )
                .peek(e -> map(event, e))
                .findAny()
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        if (id == null) return;
        events.removeIf(e -> e.getId().equals(id));
    }

    private void map(EventEntity source, EventEntity target) {
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setDateTime(source.getDateTime());
    }

    private Long generateId() {
        return nextId++;
    }
}
