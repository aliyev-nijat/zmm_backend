package az.aliyev_nijat.zmm.repository;

import az.aliyev_nijat.zmm.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository
        extends JpaRepository<EventEntity, Long> {
}
