package az.nijat_aliyev.zmm.repository;

import az.nijat_aliyev.zmm.model.entity.SliderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository
        extends JpaRepository<SliderEntity, Long> {

}
