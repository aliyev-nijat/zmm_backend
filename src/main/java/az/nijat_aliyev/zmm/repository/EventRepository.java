package az.nijat_aliyev.zmm.repository;

import az.nijat_aliyev.zmm.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository
        extends JpaRepository<EventEntity, Long> {
}
