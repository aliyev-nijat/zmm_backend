package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.mapper.EventMapper;
import az.nijat_aliyev.zmm.model.dto.EventDto;
import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.repository.EventRepository;
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
        if (event.getId() != null) return null;

        return mapper.map(repository.create(mapper.map(event)));
    }

    public List<EventEntity> findAll() {
        return repository.findAll();
    }
}
