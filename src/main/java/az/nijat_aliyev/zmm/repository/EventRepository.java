package az.nijat_aliyev.zmm.repository;

import az.nijat_aliyev.zmm.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {

    Event create(Event event);

    List<Event> findAll();

    Event getById(Long id);

    Event update(Event event);

    void delete(Long id);
}
