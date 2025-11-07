package az.nijat_aliyev.zmm.repository.impl;

import az.nijat_aliyev.zmm.model.Event;
import az.nijat_aliyev.zmm.repository.EventRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
//@Repository
public class InMemoryEventRepository implements EventRepository {

    private static List<Event> events = new LinkedList<>();
    private static Long nextId = 1L;

    @Override
    public Event create(Event event) {
        event.setId(generateId());
        events.add(event);

        return event;
    }

    @Override
    public Event getById(@NonNull Long id) {
        return events
                .stream()
                .filter(event -> id.equals(event.getId()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Event> findAll() {
        return List.copyOf(events);
    }

    @Override
    public Event update(@NonNull Event event) {
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

    private void map(Event source, Event target) {
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setDateTime(source.getDateTime());
    }

    private Long generateId() {
        return nextId++;
    }
}
