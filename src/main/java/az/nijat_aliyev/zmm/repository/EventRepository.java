package az.nijat_aliyev.zmm.repository;

import az.nijat_aliyev.zmm.model.entity.EventEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {

    EventEntity create(EventEntity event);

    List<EventEntity> findAll();

    EventEntity getById(Long id);

    EventEntity update(EventEntity event);

    void delete(Long id);
}
