package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.model.Event;
import az.nijat_aliyev.zmm.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public Event create(Event event) {
        if (event.getId() == null) return null;

        return repository.create(event);
    }

    public List<Event> findAll() {
        return repository.findAll();
    }
}
