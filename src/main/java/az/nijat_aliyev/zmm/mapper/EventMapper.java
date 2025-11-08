package az.nijat_aliyev.zmm.mapper;

import az.nijat_aliyev.zmm.model.dto.EventDto;
import az.nijat_aliyev.zmm.model.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventEntity map(final EventDto source) {
        EventEntity target = new EventEntity();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setDateTime(source.getDateTime());

        return target;
    }

    public EventDto map(final EventEntity source) {
        EventDto target = new EventDto();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setDateTime(source.getDateTime());

        return target;
    }
}
